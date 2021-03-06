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

    adminRepository.getUsersNotInGroup = function (sessionKey, groupId) {
        return $http({
            url: 'https://localhost:8082/user/not-in-group',
            method: "GET",
            params: {"sessionKey": sessionKey, "groupId":groupId}
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

    adminRepository.getUserGroups = function(sessionKey, userId) {
        return $http({
            url: 'https://localhost:8082/user/one/' + userId,
            method: "GET",
            params: {"sessionKey": sessionKey}
        });
    };

    adminRepository.getGroupUsers = function(sessionKey, groupId) {
        return $http({
            url: 'https://localhost:8082/admin/group/one/' + groupId,
            method: "GET",
            params: {"sessionKey": sessionKey}
        });
    };

    adminRepository.getAllServices = function(sessionKey) {
        return $http({
            url: 'https://localhost:8082/admin/service/all',
            method: "GET",
            params: {"sessionKey": sessionKey}
        });
    };
    adminRepository.deleteUser = function(sessionKey, userId) {
        return $http({
            url: 'https://localhost:8082/user/delete/' + userId,
            method: "GET",
            params: {"sessionKey": sessionKey}
        });
    }
    adminRepository.updateUserInfo = function(sessionKey, user, userId) {
        return $http({
            url: 'https://localhost:8082/user/update/' + userId,
            method: "POST",
            data: "login=" + user.login +
            "&name=" + user.name +
            "&email=" + user.email +
            "&phone=" + user.phone +
            "&sessionKey=" + sessionKey,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    }
    adminRepository.addNewUser = function(sessionKey, user) {
        return $http({
            url: 'https://localhost:8082/user/add/',
            method: "POST",
            data: "login=" + user.login +
            "&name=" + user.name +
            "&email=" + user.email +
            "&phone=" + user.phone +
            "&sessionKey=" + sessionKey,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    }

    adminRepository.updateUserGroups = function(sessionKey, userGroups, login) {
        return $http({
            url: 'https://localhost:8082/admin/updateGroups',
            method: "POST",
            data: "sessionKey=" + sessionKey +
            "&login=" + login +
            "&groups=" + JSON.stringify(userGroups, replacer),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    }
    adminRepository.updateUserServices = function(sessionKey, userServices, login) {
        return $http({
            url: 'https://localhost:8082/admin/updateServices',
            method: "POST",
            data: "sessionKey=" + sessionKey +
            "&login=" + login +
            "&services=" + JSON.stringify(userServices, replacer),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    }
    adminRepository.addUserToGroup = function(sessionKey, groupId, users){
        return $http({
            url:'https://localhost:8082/admin/addUserToGroup',
            method: "POST",
            data: "sessionKey=" + sessionKey +
                "&groupId=" + groupId +
                "&users=" + JSON.stringify(users, userReplacer),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    }
    adminRepository.getGroupRights = function(sessionKey, groupId) {
        return $http({
            url: 'https://localhost:8082/admin/group/rights/' + groupId,
            method: "GET",
            params: {"sessionKey": sessionKey}
        });
    }
    adminRepository.getDefaultGroupRights = function(sessionKey, groupId) {
        return $http({
            url: 'https://localhost:8082/admin/group/defaultrights/' + groupId,
            method: "GET",
            params: {"sessionKey": sessionKey}
        });
    }
    adminRepository.removeUserFromGroup = function(sessionKey, user, groupId) {
        return $http({
            url: 'https://localhost:8082/admin/group/removeUser',
            method: "GET",
            params: {"sessionKey": sessionKey, "user": JSON.stringify(user, replacer), "groupId": groupId}
        })
    }
    adminRepository.getGroupEntityRights = function(sessionKey, groupId, entityId){
        return $http({
            url: 'https://localhost:8082/admin/groups/rights/active',
            method: "GET",
            params: {"sessionKey": sessionKey, "entityId": entityId, "groupId": groupId}
        })
    }
    adminRepository.updateEntityGroupRights = function(sessionKey, groupId, rights){
        return $http({
            url: 'https://localhost:8082/admin/groups/rights/active',
            method: "POST",
            params: {"sessionKey": sessionKey, "groupId": groupId, "rights": JSON.stringify(rights, userReplacer)},
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
    }
    adminRepository.removeGroup = function(sessionKey, id){
        return $http({
            url: 'https://localhost:8082/admin/groups/remove',
            method: "POST",
            params: {"sessionKey": sessionKey, "groupId": id }
        })
    }
    adminRepository.createGroup = function(sessionKey, groupName, groupDescr){
        return $http({
            url: 'https://localhost:8082/admin/groups/create',
            method: "POST",
            params: {"sessionKey": sessionKey, "name": groupName, "descr": groupDescr }
        })
    }
    adminRepository.getServices = function(sessionKey){
        return $http({
            url: 'https://localhost:8082/admin/service/all',
            method: "GET",
            params: {"sessionKey": sessionKey}
        })
    }
    adminRepository.updateService = function(sessionKey, id, service){
        return $http({
            url: 'https://localhost:8082/admin/service/update',
            method: "POST",
            params: {"sessionKey": sessionKey, "id": id, "service": JSON.stringify(service,replacer)}
        })
    }
    adminRepository.createService = function(sessionKey, name, description, address){
        return $http({
            url:'https://localhost:8082/admin/service/create',
            method: "POST",
            params: {"sessionKey": sessionKey, "name": name, "description": description, "address" : address}
        })
    }
    adminRepository.removeService = function(sessionKey, id){
        return $http({
            url:'https://localhost:8082/admin/service/remove',
            method: "GET",
            params: {"sessionKey": sessionKey, "id": id}
        })
    }
    return adminRepository;
}]);