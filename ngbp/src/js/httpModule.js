/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
var httpModule = angular.module('httpModule', ['ngProgress']);

httpModule.service('httpRequest', ['$http', '$q', 'ngProgress', function ($http, $q, ngProgress) {
  'use strict';

  this.send = function (method, url, data) {
    ngProgress.start();
    var deferred = $q.defer();
    $http({method: method, url: url, data: data})
            .success(function (data) {
              deferred.resolve(data);
              ngProgress.complete();
            })
            .error(function (data) {
              deferred.reject(data);
              ngProgress.complete();
            });
    return deferred.promise;
  };
}]);

httpModule.factory('authInterceptor', ['$q', '$window', function ($q, $window) {
  return {
    'responseError': function (rejection) {
      console.log(rejection);
      if (rejection.status == 401) {
        console.log("status is " + rejection.status);
        $window.location = '/login';
        return;
      }
      return $q.reject(rejection);
    }
  };
}]);

httpModule.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
}]);
