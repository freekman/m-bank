/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
describe("Service", function () {

  describe("formValidator", function () {
    var fieldValidation;

    beforeEach(module("formValidator"));

    beforeEach(inject(function ($injector) {
      fieldValidation = $injector.get('fieldValidation');
    }));

    it("should validate field length", function () {
      expect(fieldValidation("")).toBeFalsy();
      expect(fieldValidation("123")).toBeTruthy();
      expect(fieldValidation(undefined)).toBeFalsy()
    });

  });

  describe("HttpRequest", function () {
    var HttpRequest;
    var httpBackend;

    beforeEach(module('ngProgress'));

    beforeEach(module('HttpModule'));

    beforeEach(inject(function ($injector) {
      httpBackend = $injector.get('$httpBackend');
      HttpRequest = $injector.get('HttpRequest');
    }));

    it("should send GET request", function () {
      httpBackend.expectGET('/test').respond('abv');

      HttpRequest.send('GET', "/test").then(function (data) {
        expect(data).toBe("abv");
      });
      httpBackend.flush();
    });

    it('should send POST request', function () {
      var params = {param: 'param1', param2: 'param2'};
      httpBackend.expectPOST('/test', params).respond('stiga ve');

      HttpRequest.send('POST', '/test', params).then(function (data) {
        expect(data).toBe('stiga ve');
      });

      httpBackend.flush();
    });

  });

});
