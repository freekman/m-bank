/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
describe("Service", function () {

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
      var result = userGateway.register("username", "pass", "pass");
      expect(httpRequest.send).toHaveBeenCalledWith("POST", "register/new", {
        username: 'username',
        password: 'pass',
        repassword: 'pass'
      });
      expect(result.promise).toEqual("dummy promise");
    });

  });

});
