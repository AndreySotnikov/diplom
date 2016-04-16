/**
 * Created by Андрей on 07.03.2016.
 */
var adminControllers = angular.module('adminControllers', []);

adminControllers.controller('adminCtrl', ['$scope', 'adminRepository', '$state', '$localStorage', '$uibModal', '$rootScope',
    function ($scope, adminRepository, $state, $localStorage,$uibModal, $rootScope) {
        adminRepository.getUserInfo($localStorage.sessionKey)
            .success(function (data) {
                $scope.name = data.name;
            })
            .error(function (data) {
                $state.go("error");
            });

        adminRepository.getAllUsers($localStorage.sessionKey)
            .success(function (data) {
                //$scope.users = data;
                $rootScope.users = data;
            });

        $scope.choose = function(user){
            $rootScope.newUser = false;
            $scope.curUser = angular.copy(user);
            adminRepository.getUserServices($localStorage.sessionKey, user.login)
                .success(function (data) {
                    $scope.curUser.services = [];
                    data.forEach(function(item, i, data){
                        $scope.curUser.services.push(item);
                    });
                })
            adminRepository.getUserGroups($localStorage.sessionKey, user.login)
                .success(function (data) {
                    $scope.curUser.groups = [];
                    data.forEach(function(item, i, data){
                        $scope.curUser.groups.push(item);
                    });
                })
        }

        $scope.delete = function(u) {
            $scope.curUser1 = angular.copy(u);
            var modalInstance = $uibModal.open({
                templateUrl: 'myModalContent.html',
                controller: 'ModalInstanceCtrl',
                resolve: {
                    user: function () {
                        return u;
                    }
                }
            });
        }

        $scope.update = function() {
            adminRepository.updateUserInfo($localStorage.sessionKey,$scope.curUser, $scope.curUser.login)
                .success(function(){
                    adminRepository.getAllUsers($localStorage.sessionKey)
                        .success(function (data) {
                            //$scope.users = data;
                            $rootScope.users = data;
                        });
                });
            adminRepository.updateUserGroups($localStorage.sessionKey,$scope.curUser.groups,$scope.curUser.login);
            adminRepository.updateUserServices($localStorage.sessionKey,$scope.curUser.services,$scope.curUser.login);
        }
    }]);
adminControllers.controller('ModalInstanceCtrl', ['$log','$scope','$uibModalInstance','adminRepository',
    '$localStorage','user','$rootScope',
    function($log,$scope,$uibModalInstance, adminRepository, $localStorage, user, $rootScope){
        $scope.delUser = user.name;
        $scope.ok = function(){
            adminRepository.deleteUser($localStorage.sessionKey, user.login)
                .success(function(){
                    $rootScope.users.splice($rootScope.users.indexOf(user),1);
                    $uibModalInstance.close();
                })
        };
        $scope.cancel = function(){
            $uibModalInstance.close();
        };
}]);
adminControllers.controller('groupsCtrl', ['$scope', 'adminRepository', '$state', '$localStorage', '$uibModal', '$rootScope',
    function ($scope, adminRepository, $state, $localStorage,$uibModal, $rootScope) {
        adminRepository.getUserInfo($localStorage.sessionKey)
            .success(function (data) {
                $scope.name = data.name;
                $scope.login = data.login;
                adminRepository.getUserGroups($localStorage.sessionKey, $scope.login)
                    .success(function (data) {
                        $scope.groups = [];
                        data.forEach(function(item, i, data){
                            $scope.groups.push(item.group);
                        });
                    })
            })
            .error(function (data) {
                $state.go("error");
            });

        $scope.choose = function(group){
            $scope.curGroup = angular.copy(group);
            adminRepository.getGroupUsers($localStorage.sessionKey, group.id)
                .success(function (data) {
                    $scope.curGroup.users = [];
                    data.forEach(function(item, i, data){
                        $scope.curGroup.users.push(item);
                    });
                })
        }

        $scope.rights = function(group){
            $state.go("rights",{id:group.id});
        }

        $scope.delete = function(u) {
            adminRepository.removeUserFromGroup($localStorage.sessionKey, u, $scope.curGroup.id)
                .success(function(data){
                    $scope.curGroup.users.splice($scope.curGroup.users.indexOf(u),1);
                });
        }

        $scope.addUser = function(g) {
            //$scope.curUser1 = angular.copy(u);
            var modalInstance = $uibModal.open({
                templateUrl: 'addUsers.html',
                controller: 'AddUserCtrl',
                resolve: {
                    groupId: function () {
                        return $scope.curGroup.id;
                    }
                }
            });
        }

    }
    ]);
adminControllers.controller('AddUserCtrl', ['$log','$scope','$uibModalInstance','adminRepository',
    '$localStorage','groupId','$rootScope',
    function($log,$scope,$uibModalInstance, adminRepository, $localStorage, groupId, $rootScope){
        adminRepository.getUsersNotInGroup($localStorage.sessionKey, groupId)
            .success(function(data){
                $scope.users = data;
            })
        $scope.ok = function(){
            var usersToAdd = [];
            $scope.users.forEach(function(item,i,data){
                if (item.added === true){
                    usersToAdd.push(item);
                }
            });
            adminRepository.addUserToGroup($localStorage.sessionKey, groupId, usersToAdd)
                .success(function (data) {
                    $uibModalInstance.close();
                })
                .error(function (data) {
                    alert("Пользователи не были добавлены");
                    $uibModalInstance.close();
                });
        };
        $scope.cancel = function(){
            $uibModalInstance.close();
        };
    }]);
adminControllers.controller('rightsCtrl', ['$scope', 'adminRepository', '$state', '$localStorage', '$uibModal', '$rootScope', '$stateParams',
    function ($scope, adminRepository, $state, $localStorage,$uibModal, $rootScope, $stateParams) {
        var groupId = $stateParams.id;
        adminRepository.getUserInfo($localStorage.sessionKey)
            .success(function (data) {
                $scope.name = data.name;
                $scope.login = data.login;
                $scope.rights = [];
                adminRepository.getGroupRights($localStorage.sessionKey, groupId)
                    .success(function (data){
                        for (var key in data) {
                            var v = data[key];
                            var str = "";
                            for (var i in v){
                                str += v[i] + "\n";
                            }
                            var elem = {
                                key: key,
                                value: str
                            }
                            $scope.rights.push(elem);
                        }
                });
            })
            .error(function (data) {
                $state.go("error");
            });

        $scope.choose = function(group){
            $scope.curGroup = angular.copy(group);
            adminRepository.getGroupUsers($localStorage.sessionKey, group.id)
                .success(function (data) {
                    $scope.curGroup.users = [];
                    data.forEach(function(item, i, data){
                        $scope.curGroup.users.push(item);
                    });
                })
        }
    }
]);