
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
                controller: 'addNewAttributeCtrl'
            });
        }
        //$scope.delUser = user.name;
        //$scope.ok = function(){
        //    studyRepository.deleteUser($localStorage.sessionKey, user.login)
        //        .success(function(){
        //            $rootScope.users.splice($rootScope.users.indexOf(user),1);
        //            $uibModalInstance.close();
        //        })
        //};
        //$scope.cancel = function(){
        //    $uibModalInstance.close();
        //};
    }]);

studyControllers.controller('addNewAttributeCtrl', ['$log','$scope','$uibModalInstance','studyRepository',
    '$localStorage','$rootScope',
    function($log,$scope,$uibModalInstance, studyRepository, $localStorage, user, $rootScope){

    }]);
