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

    //adminRepository.getAllUsers = function (sessionKey) {
    //    return $http({
    //        url: 'https://localhost:8082/user/all',
    //        method: "GET",
    //        params: {"sessionKey": sessionKey}
    //    });
    //};
    return studyRepository;

}]);