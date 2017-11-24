app.controller('mediaEditController', function($scope, $state, $stateParams, $sce, $http){
	//下拉
	$scope.mediaTypes         = [];
	$scope.states        = [];
	$scope.organizations = [];
	//实体
	$scope.media = {
		id             : $stateParams.id,
		fileName       : '',
		suffix         : '',
		url            : '',
		md5            : '',
		size           : 0/1024/1024,
		remark         : '',
		type           : '',
		state          : '',
		organizationId : ''
	};

    $scope.mediaView = '';

	$scope.getById = function(){
		$http({
			url: '/api/1.0/media/' + $scope.media.id,
			method: 'GET'
		}).success(function(res, status, headers, config){
			if(res.success){
				utils.copyOf(res.result, $scope.media);

				$scope.media.size = ($scope.media.size / 1024 / 1024);

				if($scope.media.type == 1){
					$scope.mediaView = $sce.trustAsHtml('<audio src="' + $scope.media.url + '" controls></audio>');
				}else if($scope.media.type == 2){
					$scope.mediaView = $sce.trustAsHtml('<video src="' + $scope.media.url + '" controls></video>');
				}else if($scope.media.type == 3){
					$scope.mediaView = $sce.trustAsHtml('<img src="' + $scope.media.url + '">');
				}
			}else{
				alert(res.msg);
			}
		}).error(function(response){
			console.error(response);
		});
	};

	$scope.submit = function(){
		$http({
			url: '/api/1.0/media',
			method: 'PUT',
			data: JSON.stringify($scope.media),
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(res, status, headers, config){
			if(res.success){
				alert(res.msg);
				$state.go('media');
			}else{
				alert(res.msg);
			}
		}).error(function(response){
			console.error(response);
		});
	};

	$scope.getState = function(){
		$http({
			url: '/api/1.0/usingState/list',
			method: 'GET'
		}).success(function(res, status, headers, config){
			if(res.success){
				$scope.states = res.result;
			}
		}).error(function(response){
			$scope.states = [];
		});
	};

	$scope.getOrganization = function(){
		$http({
			url: '/api/1.0/organization/list',
			method: 'POST',
			data: $.param({
				'state' : 0
			}),
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).success(function(res, status, headers, config){
			if(res.success){
				$scope.organizations = res.result;
			}
		}).error(function(response){
			$scope.organizations = [];
		});
	};

	$scope.getMediaType = function(){
		$http({
			url: '/api/1.0/mediaType/list',
			method: 'GET',
			data: $.param({
				'state' : 0
			}),
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).success(function(res, status, headers, config){
			if(res.success){
				$scope.mediaTypes = res.result;
			}
		}).error(function(response){
			$scope.mediaTypes = [];
		});
	};

	$scope.reset = function(){
		$scope.media.remark         = '';
		$scope.media.state          = '';
		$scope.media.organizationId = '';
	};

	//初始化数据
	$scope.getById();
	$scope.getState();
	$scope.getOrganization();
	$scope.getMediaType();
});