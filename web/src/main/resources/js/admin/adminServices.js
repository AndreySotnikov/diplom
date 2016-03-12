/**
 * Created by Андрей on 07.03.2016.
 */
var adminServices = angular.module('adminServices', ['ngResource']);

adminServices.factory('adminRepository', ['$http', function ($http) {
    var adminRepository = {};
    adminRepository.getUserInfo = function (sessionKey) {
        return $http({
            url: 'https://localhost:8082/user/' + sessionKey,
            method: "GET",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };

    adminRepository.getAllUsers = function (sessionKey) {
        return $http({
            url: 'https://localhost:8082/user/all',
            method: "GET",
            params: {"sessionKey": sessionKey}
        });
    };

    adminRepository.getUserServices = function(sessionKey, userId) {
        return $http({
            url: 'https://localhost:8082/service-user/all',
            method: "GET",
            params: {"sessionKey": sessionKey, "userId": userId}
        });
    };

    adminRepository.getUserServices = function(sessionKey, userId) {
        return $http({
            url: 'https://localhost:8082/admin/service-user/all',
            method: "GET",
            params: {"sessionKey": sessionKey, "userId": userId}
        });
    };

    adminRepository.getAllServices = function(sessionKey) {
        return $http({
            url: 'https://localhost:8082/admin/service/all',
            method: "GET",
            params: {"sessionKey": sessionKey}
        });
    };
    //adminRepository.register = function (login, token, name, email, phone) {
    //    return $http({
    //        url: 'https://localhost:8082/register',
    //        method: "POST",
    //        data: "login=" + login +
    //        "&token=" + token +
    //        "&name=" + name +
    //        "&email=" + email +
    //        "&phone=" + phone,
    //        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    //    });
    //};
    return adminRepository;
}]);