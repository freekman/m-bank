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
              console.log("httpModule success:");
              console.log(data);
              deferred.resolve(data);
              ngProgress.complete();
            })
            .error(function (data) {
              console.log("httpModule error:");
              console.log(data);
              deferred.reject(data);
              ngProgress.complete();
            });
    return deferred.promise;
  };
}]);

httpModule.factory('authInterceptor', ['$q', function ($q) {
  return {
    //// optional method
    //'request': function (config) {
    //  // do something on success
    //  return config;
    //},
    //
    //// optional method
    //'requestError': function (rejection) {
    //  // do something on error
    //  if (canRecover(rejection)) {
    //    return responseOrNewPromise
    //  }
    //  return $q.reject(rejection);
    //},


    // optional method
    'response': function (response) {
      console.log("interceptor success:");
      console.log(response);
      if (response.status == 401) {
        console.log("Repose 401");
      }

      return response;
    },

    // optional method
    'responseError': function (rejection) {
      console.log("interceptor error:");
      console.log(rejection);

      return $q.reject(rejection);
    }
  };
}]);

httpModule.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
}]);
