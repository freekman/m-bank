/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
describe("Service", function () {
  'use strict';

  describe("Gateway", function () {
    var userGateway;
    var httpRequest;

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
      var dummyUser = {name: "Dummy", password: "pass", repassword: "pass"};
      var result = userGateway.register(dummyUser);
      expect(httpRequest.send).toHaveBeenCalledWith("POST", "register/new",
              {username: 'Dummy', password: 'pass', repassword: 'pass'});
      expect(result.promise).toEqual("dummy promise");
    });

    it('should lookup username', function () {
      var username = 'dummy_name';
      var result = userGateway.lookup(username);
      expect(httpRequest.send).toHaveBeenCalledWith('GET', 'register/new', username);
      expect(result.promise).toEqual('dummy promise');
    });

  });

});
