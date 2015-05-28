/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankApp = angular.module('bankApp', ['ngRoute', 'bankCtrl']);

bankApp.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
          .when('/register', {
            templateUrl: 'partials/register.html',
            controller: 'registerCtrl'
          })
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