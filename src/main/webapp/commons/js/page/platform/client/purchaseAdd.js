app.controller('purchaseAddController', function($scope, $http, $state, $stateParams){
    //下拉
    $scope.states        = {};
    $scope.products      = [];
    $scope.organizations = [];
    $scope.product       = {};
    $scope.state         = '';

    //实体
    $scope.agreement = {
        accountId        : $stateParams.accountId,
        client           : '',
        phone            : '',
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
        state            : $scope.state,
        services         : [],
        retail           : 0,
        price            : 0,
        duration         : 0
    };

    $scope.purchase = {
        accountId      : $stateParams.accountId,
        organizationId : $stateParams.organizationId,
        agreement      : $scope.agreement,
        state          : $scope.state,
        purchaseState  : '0'
    };

    //按钮
    $scope.serviceAdd = function(){
        var service = {
            name        : '',
            description : '',
            time        : 0,
            unitPrice   : 0
        };

        $scope.agreement.services.push(service);
    };

    $scope.serviceDelete = function(index){
        $scope.agreement.services.splice(index, 1);
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/purchase',
            method: 'POST',
            data: JSON.stringify($scope.purchase),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('client');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    //查询数据
    $scope.getState = function(){
        $http({
            url: '/api/1.0/usingState/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.states = res.result;
            }
        }).error(function(response){
            $scope.states = {};
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

    $scope.getOrganization = function(){
        $http({
            url: '/api/1.0/organization/' + $scope.purchase.organizationId,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.agreement.company        = res.result.company;
                $scope.agreement.licenseNumber  = res.result.licenseNumber;
                $scope.agreement.legalPerson    = res.result.legalPerson;
                $scope.agreement.companyAddress = res.result.companyAddress;
                $scope.agreement.companyPhone   = res.result.companyPhone;
                $scope.agreement.companyEmail   = res.result.companyEmail;
                $scope.agreement.companyWebsite = res.result.companyWebsite;
            }
        }).error(function(response){
            console.error(response);
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

    $scope.$watch('state', function(newVal, oldVal){
        if(newVal === null){
            $scope.agreement.state = 0;
            $scope.purchase.state  = 0;
        }else if(newVal !== oldVal){
            $scope.agreement.state = newVal;
            $scope.purchase.state  = newVal;
        }
    }, true);

    //初始化数据
    $scope.getState();
    $scope.getProduct();
    $scope.getOrganization();
});