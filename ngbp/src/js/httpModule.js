/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var httpModule = angular.module('httpModule', ['ngProgress']);

httpModule.service('httpRequest', ['$http', '$q', 'ngProgress', function ($http, $q, ngProgress) {
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
  }
}]);
