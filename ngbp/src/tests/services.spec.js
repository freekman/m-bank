/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
describe("Service", function () {
  'use strict';

  describe("Gateway", function () {
    var userGateway;
    var httpRequest;

    var dummy = {name: "Dummy", password: "pass", repassword: "pass"};

    beforeEach(module('gateway'));

    beforeEach(function () {

      httpRequest = {send: jasmine.createSpy().andReturn({promise: 'dummy promise'})};

      module(function ($provide) {
        $provide.value("httpRequest", httpRequest);
      });

      inject(function ($injector) {
        userGateway = $injector.get("userGateway");
      });

    });

    it("should register user", function () {
      var result = userGateway.register(dummy);
      expect(httpRequest.send).toHaveBeenCalledWith("POST", "register/new",
              {username: 'Dummy', password: 'pass', repassword: 'pass'});
      expect(result.promise).toEqual("dummy promise");
    });

    it('should lookup username', function () {
      var result = userGateway.lookup(dummy);
      expect(httpRequest.send).toHaveBeenCalledWith('GET', 'register/new?username=' + dummy.name, {});
      expect(result.promise).toEqual('dummy promise');
    });

  });

});
