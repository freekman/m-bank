/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
var gatewayModule = angular.module('gateway', ['httpModule']);

gatewayModule.service('userGateway', ['httpRequest', function (HttpRequest) {
  'use strict';
  return {
    lookup: function (user) {
      return HttpRequest
              .send('GET', 'register/new?username=' + user.name, {});
    },
    register: function (user) {
      console.log(user);
      return HttpRequest
              .send('POST', 'register/new',
              {
                username: user.name,
                password: user.password,
                repassword: user.repassword
              });
    }
  };
}]);
