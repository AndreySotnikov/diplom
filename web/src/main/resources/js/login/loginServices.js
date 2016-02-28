/**
 * Created by Андрей on 28.02.2016.
 */
var loginServices = angular.module('loginServices', ['ngResource']);

loginServices.factory('User', ['$resource',
    function($resource){
        return $resource('https://localhost:8082/:action', {
                login:"@login",
                token:"@token",
                action:"@action"
            },
            {
            login: {method:'POST',
                params:{action:'login'},
                isArray:false
            },
            register: {method:'POST',
                params:{action:'register'},
                isArray:false
            }
        });
    }]);