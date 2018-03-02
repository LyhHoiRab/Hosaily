app.controller('purchaseEditController', function($scope, $http, $state, $stateParams){
    //下拉
    $scope.products      = [];

    //实体
    $scope.purchase = {
        id : $stateParams.id
    };

    $scope.agreement = {
        id               : '',
        //accountId        : $stateParams.accountId,
        client           : '',
        phone            : '',
        address          : '',
        idCard           : '',
        wechat           : '',
        email            : '',
        emergencyContact : '',
        company          : '',
        licenseNumber    : '',
        legalPerson      : '',
        companyAddress   : '',
        companyPhone     : '',
        companyEmail     : '',
        companyWebsite   : '',
        version          : '',
        //state            : $scope.state,
        services         : [],
        retail           : 0,
        price            : 0,
        duration         : 0
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/agreement',
            method: 'PUT',
            data: JSON.stringify($scope.agreement),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('purchase');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    //查询数据
    $scope.getAgreementById = function(){
        $http({
            url: '/api/1.0/agreement/purchaseId/' + $scope.purchase.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.agreement);
                $scope.state = $scope.agreement.state;
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getProduct = function(){
        $http({
            url: '/api/1.0/product/list',
            method: 'POST',
            data: $.param({
                'organizationId' : $scope.purchase.organizationId,
                'state' : 0
            })
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.products = res.result;
            }
        }).error(function(response){
            $scope.products = [];
        });
    };

    $scope.$watch('product', function(newVal, oldVal){
        if(newVal === null){
            $scope.agreement.price = 0;
            $scope.agreement.retail = 0;
            $scope.agreement.services = [];
            $scope.agreement.duration = 0;

        }else if(newVal !== oldVal){
            $scope.agreement.price = newVal.price;
            $scope.agreement.retail = newVal.price;
            $scope.agreement.services = newVal.services;
            $scope.agreement.duration = newVal.duration;
        }
    }, true);

    $scope.$watch('product', function(newVal, oldVal){
        if(newVal === null){
            $scope.agreement.price = 0;
            $scope.agreement.retail = 0;
            $scope.agreement.services = [];
            $scope.agreement.duration = 0;

        }else if(newVal !== oldVal){
            $scope.agreement.price = newVal.price;
            $scope.agreement.retail = newVal.price;
            $scope.agreement.services = newVal.services;
            $scope.agreement.duration = newVal.duration;
        }
    }, true);

    //初始化数据
    $scope.getAgreementById();
    $scope.getProduct();
});