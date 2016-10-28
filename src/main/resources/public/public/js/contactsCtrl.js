var app = angular.module('phonebook');

app.controller('contactsCtrl',function($scope, $location, authStore,$http){
	if (!authStore.isAuth()) {
		$location.path('/signin');	
	}

	$scope.formShow = false;
	$scope.showForm = function(){
		$scope.formShow = ! $scope.formShow ;
	}
	$scope.state = "list";
	$scope.mode = "add";
	$scope.filter = {
		firstName : "",
        lastName : "",
        phoneNumber: ""
	};
	$scope.errors = [];
	$scope.contact = {};

	$scope.contacts = [];

	$scope.openAdd = function(){
		$scope.state = "add";
		$scope.mode = "add";
	}

	$scope.cancel = function(){
		$scope.state = "list";
		$scope.contact = {};
	}

	$scope.edit = function(contact){
		$scope.contact = contact;
		$scope.state = "add";
		$scope.mode = "update";
	}

	$scope.clearFilter = function(){
		$scope.filter = {
		firstName : "",
        lastName : "",
        phoneNumber: ""
	};
	}

	$scope.getContacts = function(){
		var res = $http.get('/api/v1/contact/', {
        params: {
           	firstName : $scope.filter.firstName,
            lastName : $scope.filter.lastName,
            phoneNumber: $scope.filter.phoneNumber
        }
     })
		res.success(function(data, status, headers, config) {
			$scope.contacts = data;
		});
	}



	$scope.deleteContact = function(contact){
		var res = $http.delete('/api/v1/contact/'+contact.id);
		res.success(function(data, status, headers, config) {
			$scope.getContacts();
		});
	}

	$scope.updateContact = function(){
		var data = JSON.stringify($scope.contact);
		var res = $http.put('/api/v1/contact/'+$scope.contact.id, data);

		res.success(function(data, status, headers, config) {
			$scope.getContacts();
			$scope.state = "list";
			$scope.contact = {};
		});

		res.error(function(data, status, headers, config) {
			$scope.errors = data.errors;
		});	
	}

	$scope.addContact = function(){
		var data = JSON.stringify($scope.contact);
		var res = $http.post('/api/v1/contact/', data);
		res.success(function(data, status, headers, config) {
			alert("Contact added");
			$scope.getContacts();
			$scope.state = "list";
			$scope.contact = {};
		});

		res.error(function(data, status, headers, config) {
			$scope.errors = data.errors;
		});	
	}

	$scope.getContacts();
	
}); 