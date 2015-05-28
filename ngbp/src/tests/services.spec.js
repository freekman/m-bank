/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
describe("Service", function () {
  'use strict';

  describe("userGateway", function () {
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
      expect(httpRequest.send).toHaveBeenCalledWith("POST", "/r/register",
              {username: 'Dummy', password: 'pass', repassword: 'pass'});
      expect(result.promise).toEqual("dummy promise");
    });

    it('should lookup username', function () {
      var result = userGateway.lookup(dummy);
      expect(httpRequest.send).toHaveBeenCalledWith('GET', '/r/register?username=' + dummy.name, {});
      expect(result.promise).toEqual('dummy promise');
    });

  });


  describe("accGateway", function () {
    var accGateway;
    var httpRequest;

    beforeEach(module('gateway'));

    beforeEach(function () {

      httpRequest = {send: jasmine.createSpy().andReturn({promise: 'dummy promise'})};

      module(function ($provide) {
        $provide.value("httpRequest", httpRequest);
      });

      inject(function ($injector) {
        accGateway = $injector.get("accGateway");
      });

    });

    it('should send deposit request', function () {
      var amount = 10;
      var result = accGateway.deposit(amount);
      expect(httpRequest.send).toHaveBeenCalledWith("POST", "/r/deposit", {amount: amount});
      expect(result.promise).toEqual('dummy promise');
    });

    it('should send withdraw request', function () {
      var amount = 20;
      var result = accGateway.withdraw(amount);
      expect(httpRequest.send).toHaveBeenCalledWith('POST', '/r/withdraw', {amount: amount});
      expect(result.promise).toEqual('dummy promise');
    });

  });

});

