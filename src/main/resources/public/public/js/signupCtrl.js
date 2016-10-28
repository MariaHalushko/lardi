var app = angular.module('phonebook');

app.controller('signupCtrl',function($scope, $http,  $location, authStore){
	
	$scope.account = {
		login:"",
		password:"",
		fullName:"",
	};

	$scope.cancel = function(){
		$location.path('/');
	};

	$scope.errors = [];

	$scope.registration = function(){
		var data = JSON.stringify($scope.account);
		var res = $http.post('/api/v1/account/', data);
		res.success(function(data, status, headers, config) {
			alert("Registration complete")
			$location.path('/');
		});

		res.error(function(data, status, headers, config) {
			$scope.errors = data.errors;
		});	
	};
});