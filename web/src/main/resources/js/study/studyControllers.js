
var studyControllers = angular.module('studyControllers', []);

studyControllers.controller('studyCtrl', ['$scope', 'studyRepository', '$state', '$localStorage','$uibModal',
    function ($scope, studyRepository, $state, $localStorage, $uibModal) {
        studyRepository.getAllFiles($localStorage.sessionKey)
            .success(function (data) {
                $scope.files = data;
            })
            .error(function (data) {
                $state.go("error");
            });
        studyRepository.getAllSubs($localStorage.sessionKey)
            .success(function (data) {
                $scope.subs = data;
            })
            .error(function (data) {
                $state.go("error");
            });
        $scope.addNewFile = function() {
            var modalInstance = $uibModal.open({
                templateUrl: 'addNewFileModal',
                controller: 'addNewFileCtrl'
            });
        }
    }]);

studyControllers.controller('addNewFileCtrl', ['$log','$scope','$uibModalInstance','studyRepository',
    '$localStorage','$rootScope', '$uibModal',
    function($log,$scope,$uibModalInstance, studyRepository, $localStorage, $rootScope, $uibModal){
        $scope.addNewAttribute = function() {
            var modalInstance = $uibModal.open({
                templateUrl: 'addNewAttributeModal',
                controller: 'addNewAttributeCtrl',
            });
            modalInstance.closed.then(function () {
                $scope.refresh();
            }, function() {
                $scope.refresh();
            })
        }

        $scope.refresh = function() {
            alert("check");
        }
    }]);

studyControllers.controller('addNewAttributeCtrl', ['$log','$scope','$uibModalInstance','studyRepository',
    '$localStorage', '$rootScope',
    function($log,$scope,$uibModalInstance, studyRepository, $localStorage, $rootScope){
        $scope.addNewAttribute = function(name) {
            studyRepository.addAttribute($localStorage.sessionKey, name)
                .success(function (data) {
                    $uibModalInstance.close();
                })
                .error(function (data) {
                    alert("fail");
                    $uibModalInstance.close();
                });
        }
        $scope.closeAttributeModal = function() {
            $uibModalInstance.close();
        }
    }]);
