/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankApp = angular.module('bankApp',
        ['ngRoute',
          'bankControllers']);

bankApp.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
          .when('/', {
            templateUrl: 'partials/register.html',
            controller: 'RegisterController'
          }).when('/register', {
            templateUrl: 'partials/register.html',
            controller: 'RegisterController'
          })
          .otherwise({redirectTo: '/phones'});
}]);