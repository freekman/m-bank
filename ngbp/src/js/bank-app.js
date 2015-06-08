/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankApp = angular.module('bankApp', ['ngRoute', 'bankCtrl']);

bankApp.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
          .when('/account', {
            templateUrl: 'partials/account.html',
            controller: 'accountCtrl'
          })
          .otherwise({
            controller: function () {
              window.location.replace('/login');
            },
            template: "<div></div>"
          });
}]);