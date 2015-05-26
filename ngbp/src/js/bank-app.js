/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankApp = angular.module('bankApp', ['ngRoute', 'bankCtrl']);

bankApp.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
          .when('/', {
            templateUrl: 'partials/register.html',
            controller: 'registerCtrl'
          }).when('/register', {
            templateUrl: 'partials/register.html',
            controller: 'registerCtrl'
          })
          .when('/test', {
            templateUrl: 'partials/inputTest.html',
            controller: 'inputCtrl'
          })
          .when('/account', {
            templateUrl: 'partials/account.html',
            controller: 'accountCtrl'
          })
          .otherwise({redirectTo: '/phones'});
}]);