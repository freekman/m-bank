/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
describe("Service", function () {

  describe("Gateway", function () {
    var UserGateway;
    var HttpRequest;
    var deferred;

    beforeEach(module('Gateway'));

    beforeEach(function () {

      HttpRequest = {send: jasmine.createSpy().andReturn({promise: 'dummy promise'})};

      module(function ($provide) {
        $provide.value("HttpRequest", HttpRequest);
      });

      inject(function ($injector) {
        UserGateway = $injector.get("UserGateway");
      });

    });

    it("should register user", function () {
      var result = UserGateway.register("username", "pass", "pass");
      expect(HttpRequest.send).toHaveBeenCalledWith("POST", "register/new", {
        username: 'username',
        password: 'pass',
        repassword: 'pass'
      });
      expect(result.promise).toEqual("dummy promise");
    });

  });

});
