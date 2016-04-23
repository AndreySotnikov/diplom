/**
 * Created by Андрей on 07.03.2016.
 */
var studyServices = angular.module('studyServices', ['ngResource']);

studyServices.factory('studyRepository', ['$http', function ($http) {
    var studyRepository = {};
    studyRepository.getAllFiles = function (sessionKey) {
        return $http({
            url: 'https://localhost:8081/files/all',
            params: {"sessionKey": sessionKey},
            method: "GET",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };

    studyRepository.getAllSubs = function (sessionKey) {
        return $http({
            url: 'https://localhost:8081/subs/all',
            params: {"sessionKey": sessionKey},
            method: "GET",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };

    studyRepository.getAttributes = function (sessionKey) {
        return $http({
            url: 'https://localhost:8081/attributes/all',
            params: {"sessionKey": sessionKey},
            method: "GET",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    }

    studyRepository.addAttribute = function (sessionKey, name) {
        return $http({
            url: 'https://localhost:8081/attributes/add',
            params: {"sessionKey": sessionKey, "name": name},
            method: "POST",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };

    studyRepository.confirmedDelete = function (sessionKey, id) {
        return $http({
            url: 'https://localhost:8081/files/deleteFile',
            params: {"sessionKey": sessionKey, "fileId": id},
            method: "GET",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };

    studyRepository.addNewFile = function (sessionKey, file, descr, name, attrs) {
        return $http({
            url: 'https://localhost:8081/files/uploadnewfile',
            params: {"sesionKey": sessionKey, "file": file,
                     "descr": descr, "name": name, "attrs": attrs},
            method: "POST",
            headers: {'Content-Type': 'multipart/form-data'}
        });
    }

    return studyRepository;

}]);