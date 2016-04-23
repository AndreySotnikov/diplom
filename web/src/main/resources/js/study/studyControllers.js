
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

        $scope.refresh = function() {
            studyRepository.getAllFiles($localStorage.sessionKey)
                .success(function (data) {
                    $scope.files = data;
                });
        }

        $scope.deleteFile = function(file) {
            var modalInstance = $uibModal.open({
                templateUrl: 'deleteFileModal',
                controller: 'deleteFileCtrl',
                resolve: {
                    file: function () {
                        return file;
                    }
                }
            });
            modalInstance.closed.then(function () {
                $scope.refresh();
            }, function() {
                $scope.refresh();
            })
        }
    }]);

studyControllers.controller('deleteFileCtrl', ['$log','$scope','$uibModalInstance','studyRepository',
    '$localStorage','$rootScope', '$uibModal', 'file',
    function($log,$scope,$uibModalInstance, studyRepository, $localStorage, $rootScope, $uibModal, file ){
        $scope.file = file;
        $scope.confirmed = function() {
            studyRepository.confirmedDelete($localStorage.sessionKey, file.id);
            $uibModalInstance.close();
        }

        $scope.close = function() {
            $uibModalInstance.close();
        }
    }]);

studyControllers.controller('addNewFileCtrl', ['$log','$scope','$uibModalInstance','studyRepository',
    '$localStorage','$rootScope', '$uibModal',
    function($log,$scope,$uibModalInstance, studyRepository, $localStorage, $rootScope, $uibModal){
        $scope.attributes = [];

        getAttributes = function() {
            studyRepository.getAttributes($localStorage.sessionKey)
                .success(function (data) {
                    $scope.attributes = data;
                })
                .error(function (data) {
                    $state.go("error");
                });
        }

        getAttributes();
        $scope.addNewAttribute = function() {
            var modalInstance = $uibModal.open({
                templateUrl: 'addNewAttributeModal',
                controller: 'addNewAttributeCtrl'
            });
            modalInstance.closed.then(function () {
                getAttributes();
            }, function() {
                getAttributes();
            })
        }

        getDropDown = function () {
            var result = '<select class="selectpicker>"';
            $scope.attributes.forEach(function(item, i, arr) {
                var elem = "<option value=" + item.id +">" + item.name + "</option>";
                result += elem;
            });
            result += "</select>";
            return result;
        }

        getInputPair = function() {
            var attr = getDropDown();
            var result = "<tr><td>";
            result += attr;
            result += "</td><td>";
            result += '<input class="form-control" type="text">';
            result += "</td></tr>";
            return result;
        }

        $scope.insertAttribute = function() {
            var pair = getInputPair();
            $("#fileform").append(pair);
        }

        $scope.addNewFile = function(descr, name) {
            var file = $("#fileupload").value();
            studyRepository.addNewFile($localStorage.sessionKey, file, descr, name, "")
                .success(function (data) {
                    $uibModalInstance.close();
                })
                .error(function (data) {
                    $uibModalInstance.close();
                });
        }

        $scope.setFile = function(file) {
            $scope.taks = file;
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
                    $uibModalInstance.close();
                });
        }
        $scope.closeAttributeModal = function() {
            $uibModalInstance.close();
        }
    }]);
