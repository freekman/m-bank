/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var GatewayModule = angular.module('Gateway', ['HttpModule']);
//
//serviceModule.service('fieldValidation', function () {
//  this.validate = function (field) {
//    return !(field == null || field.length < 3);
//  }
//});

GatewayModule.service('UserGateway', ['HttpRequest', function (HttpRequest) {
  return {
    register: function (username, password, repassword) {
      return HttpRequest
              .send('POST', 'register/new'
              , {username: username, password: password, repassword: repassword});
    }
  };
}]);

