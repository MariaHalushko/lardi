var app = angular.module('phonebook',['ngRoute','authService']);

app.config(function($routeProvider, $locationProvider){
	$routeProvider
	.when('/signin',{
		templateUrl: '/public/partials/signin.html',
	})
	.when('/signup',{
		templateUrl: '/public/partials/signup.html',
	})
	.when('/',{
		templateUrl: '/public/partials/contacts.html',
	})
	.otherwise('/');
});

app.config(['$httpProvider', function($httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }
]);

