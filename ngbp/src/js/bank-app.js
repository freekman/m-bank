/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankApp = angular.module('bankApp', ['ngRoute', 'bankCtrl']);

bankApp.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
          .when('/', {
            templateUrl: 'partials/register.html',
            controller: 'registerCtrl'
          }).when('/r/register', {
            templateUrl: 'partials/register.html',
            controller: 'registerCtrl'
          })
          .when('/account', {
            templateUrl: 'partials/account.html',
            controller: 'accountCtrl'
          })
          .otherwise({redirectTo: '/phones'});
}]);