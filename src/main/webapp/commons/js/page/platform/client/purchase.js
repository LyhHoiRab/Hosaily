app.controller('purchaseController', function($scope, $http, $state, $stateParams){
    $scope.accountId = $stateParams.accountId;
    $scope.organizationId = $stateParams.organizationId;

    console.log($scope.accountId);
    console.log($scope.organizationId);
});