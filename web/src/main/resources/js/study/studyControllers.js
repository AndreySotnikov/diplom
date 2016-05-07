
var studyControllers = angular.module('studyControllers', []);

studyControllers.controller('studyCtrl', ['$scope', 'studyRepository', '$state', '$localStorage','$uibModal',
    function ($scope, studyRepository, $state, $localStorage, $uibModal) {
        $scope.refreshFiles = function() {
            studyRepository.getAllFiles($localStorage.sessionKey)
                .success(function (data) {
                    $scope.files = data;
                })
                .error(function (data) {
                    $state.go("error");
                });
        }

        $scope.refreshFiles();

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
            modalInstance.closed.then(function () {
                $scope.refreshFiles();
            }, function() {
                $scope.refreshFiles();
            })
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
                $scope.refreshFiles();
            }, function() {
                $scope.refreshFiles();
            })
        }

        $scope.showRevisions = function(file) {
            $state.go("revisions", {fileId: file.id, name: file.name});
        }
    }
]);

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
    }
]);

studyControllers.controller('addNewFileCtrl', ['$log','$scope','$uibModalInstance','studyRepository',
    '$localStorage','$rootScope', '$uibModal','$http',
    function($log,$scope,$uibModalInstance, studyRepository, $localStorage, $rootScope, $uibModal, $http){
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
            var result = '<select class="selectpicker">';
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

        $scope.close = function() {
            $uibModalInstance.close();
        }

        $scope.addNewFile = function(descr, name) {
            var file = document.getElementById("fileupload").files[0];
            var formData = new FormData();
            formData.append('file', file);
            formData.append('sessionKey', $localStorage.sessionKey);
            formData.append('name', name);
            formData.append('descr', descr);
            formData.append('attrs', '');
            $http.post('https://localhost:8081/files/uploadnewfile',formData,{
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            $uibModalInstance.close();
        }

        $scope.setFile = function(file) {
            $scope.taks = file;
        }
    }
]);

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
    }
]);

studyControllers.controller('revisionsCtrl', ['$scope', 'studyRepository', '$state', '$localStorage',
        '$uibModal', '$rootScope', '$stateParams',
    function ($scope, studyRepository, $state, $localStorage,$uibModal, $rootScope, $stateParams) {
        $scope.filename = $stateParams.name;
        $scope.fileid = $stateParams.fileId;

        $scope.getRevisions = function() {
            studyRepository.getRevisions($localStorage.sessionKey, $scope.fileid)
                .success(function (data) {
                    $scope.revisions = data;
                })
                .error(function (data) {
                    $state.go("error");
                });
        }

        $scope.getRevisions();

        $scope.addNewRevisionModal = function() {
            var modalInstance = $uibModal.open({
                templateUrl: 'addNewRevModal',
                controller: 'addNewRevCtrl',
                resolve: {
                    fileId: function () {
                        return $scope.fileid;
                    },
                    fileName: function() {
                        return $scope.filename;
                    }
                }
            });
            modalInstance.closed.then(function () {
                $scope.getRevisions();
            }, function() {
                $scope.getRevisions();
            })
        }

        $scope.backToFiles = function() {
            $state.go("study");
        }

        $scope.downloadRev = function(revId) {
            var prefix = "https://localhost:8081/files/downloadFile?sessionKey=" + $localStorage.sessionKey + "&revId=";
            window.location.href = prefix + revId;
        }
    }
]);

studyControllers.controller('addNewRevCtrl', ['$log','$scope','$uibModalInstance','studyRepository',
    '$localStorage','$rootScope', '$uibModal', 'fileId', 'fileName', '$http',
    function($log,$scope,$uibModalInstance, studyRepository, $localStorage, $rootScope, $uibModal, fileId, fileName, $http){
        $scope.fileId = fileId;
        $scope.filename = fileName;
        $scope.addNewRevision = function(descr) {
            var newFile = document.getElementById("fileupload").files[0];
            var formData = new FormData();
            formData.append('file', newFile);
            formData.append('fileid', fileId);
            formData.append('sessionKey', $localStorage.sessionKey);
            formData.append('descr', descr);
            $http.post('https://localhost:8081/files/uploadnewrevision',formData,{
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            $uibModalInstance.close();
        }

        $scope.cancel = function() {
            $uibModalInstance.close();
        }
    }
]);