/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var gatewayModule = angular.module('gateway', ['httpModule']);

gatewayModule.service('userGateway', ['httpRequest', function (HttpRequest) {
  return {
    register: function (username, password, repassword) {
      return HttpRequest
              .send('POST', 'register/new'
              , {username: username, password: password, repassword: repassword});
    }
  };
}]);
