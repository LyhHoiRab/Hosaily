app.controller('mediaEditController', function($scope, $state, $stateParams, $sce){
	$scope.media = {
		id       : '',
		fileName : '',
		suffix   : '',
		url      : '',
		md5      : '',
		size     : '',
		remark   : '',
		type     : '',
		state    : ''
	};
	$scope.id = $stateParams.id;
	$scope.type = {
    	0: '未知',
    	1: '音频',
    	2: '视频',
    	3: '图片',
    	4: '文本'
    };
    $scope.mediaView = '';

	$scope.getById = function(){
		$.ajax({
			url: '/api/1.0/media/' + $scope.id,
			type: 'GET',
			dataType: 'JSON',
			success: function(res){
				if(res.success){
					//$scope.media = res.result;
					utils.copyOf(res.result, $scope.media);

					if($scope.media.type == 1){
						$scope.mediaView = $sce.trustAsHtml('<audio src="' + $scope.media.url + '" controls></audio>');
					}else if($scope.media.type == 2){
						$scope.mediaView = $sce.trustAsHtml('<video src="' + $scope.media.url + '" controls></video>');
					}else if($scope.media.type == 3){
						$scope.mediaView = $sce.trustAsHtml('<img src="' + $scope.media.url + '">');
					}
				}

				if(!$scope.$$phase){
                    $scope.$apply();
                }
			}
		});
	};

	$scope.submit = function(){
		$.ajax({
			url: '/api/1.0/media',
			type: 'PUT',
			dataType: 'JSON',
			contentType: 'application/json',
			data: JSON.stringify($scope.media),
			success: function(res){
				if(res.success){
					alert(res.msg);
					$state.go('media');
				}
			}
		});
	};

	$scope.reset = function(){
		$scope.media.remark = '';
		$scope.media.state = '';
	};

	$scope.getById();
});