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
}])
