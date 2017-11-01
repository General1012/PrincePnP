

var myApp = angular.module("myApp", ["ngRoute"]);

myApp.config(function ($routeProvider, $locationProvider) {
    alert("config active");
    $routeProvider.when("/", {
        templateUrl: "home.html",
        controller: "reg_log_Controller"
    }).when("/register", {
        templateUrl: "register.html",
        controller: "reg_log_Controller"
    }).when("/login", {
        templateUrl: "login.html",
        controller: "reg_log_Controller"
    }).when("/home", {
        templateUrl: "home.html",
        controller: "reg_log_Controller"
    }).when("/admin", {
        templateUrl: "admin.html",
        controller: "adminController"
    }).when("/addproduct", {
        templateUrl: "addproduct.html",
        controller: "reg_log_Controller"
        ////////////////////////////////////////////////////////////
    }).when("/addproduct", {
        templateUrl: "addproduct.html",
        controller: "addproduct_Controller"
    }).when("/theproduct", {
        templateUrl: "theproduct.html",
        controller: "reg_log_Controller"
    }).when("/cart", {
        templateUrl: "cart.html",
        controller: "reg_log_Controller"
    }).when("/aboutus", {
        templateUrl: "aboutus.html",
        controller: "reg_log_Controller"
    }).when("/edit_deleteproduct", {
        templateUrl: "edit_deleteproduct.html",
        controller: "reg_log_Controller"
    }).when("/account", {
        templateUrl: "account.html",
        controller: "reg_log_Controller"
     }).when("/vieworders", {
        templateUrl: "vieworders.html",
        controller: "reg_log_Controller"
        }).when("/viewaccounts", {
        templateUrl: "viewaccounts.html",
        controller: "reg_log_Controller"
        }).when("/thankyou", {
        templateUrl: "thankyou.html",
        controller: "reg_log_Controller"
        }).when("/adminlogin", {
        templateUrl: "adminlogin.html",
        controller: "reg_log_Controller"
    }).otherwise("/");
});

myApp.controller("addproduct_Controller", function ($scope, $http, $location, $rootScope) {
    alert("crazzzzzzzzzzzzyyyyyyyyyyyyyyyy");
    $rootScope.goto = function (url) {
        $location.path(url);
    };
    
    $scope.image = null;

    var name = $scope.name;
    var description = $scope.description;
    var price = $scope.price;
    var category = $scope.category;


    var imageCopy = null;
    var image = null;

    var handleImageSelect = function (evt) {
        var files = evt.target.files;
        var file = files[0];

        if (files && file) {
            var reader = new FileReader();
            reader.onload = function (readerEvt) {
                var binaryString = readerEvt.target.result;
                imageCopy = btoa(binaryString);
                image = 'data:image/octet-stream;base64,' + imageCopy;
                $scope.image = image;
            };
            reader.readAsBinaryString(file);
        }
    };
//////////////////////////////////////////////////////////////////////////////////////////////
    if (window.File && window.FileReader && window.FileList && window.Blob) {
        document.getElementById('filePickerImage').addEventListener('change', handleImageSelect, false);
    } else {
        alert('Please Select Images Only');
    }

    $scope.addproduct = function () {

        $scope.addCount = 0;

        $http.post("/addproduct", {
            name: $scope.name,
            description: $scope.description,
            price: $scope.price,
            category: $scope.category,
            quantity: 0,
            image: $scope.image
        }).then(function (map) {

            $scope.status = map.data.status;

            if ($scope.status === 'success') {
                $scope.addCount++;
                alert(map.data.message);


                var id = $("#prodId");
                var name = $("#prodname");
                var description = $("#proddescription");
                var price = $("#prodprice");
                var category = $("#category");
           

                if (id !== "" || name !== "" || description !== "" || price !== "" || category !== "" ) {
                    id = "";
                    name = "";
                    description = "";
                    price = "";
                    category = "";

                }
                
           $scope.goto("/admin");
            } else {
                alert("Something Went Wrong");
            }
        });
    };

});

myApp.controller("homeController", function ($scope, $http, $location, $rootScope) {
    alert("home");
    $rootScope.goto = function (url) {
        $location.path(url);
    };

});
myApp.controller("reg_log_Controller", function ($scope, $http, $location, $rootScope ,PostToServerService, UserGetService, ProductGetService) {
    alert("reg_log_controller");
    $rootScope.goto = function (url) {
        $location.path(url);
    };
    //---------------data that is loaded every time the controller is loaded----------------------
    $rootScope.inUser = angular.fromJson(sessionStorage.getItem("userIn"));

    
    $scope.selectedList = [];
    $rootScope.loginStatus = sessionStorage.getItem("loginStatus");
   










       $scope.register = function () {

    var reqUrl = "/register";
        var user = $scope.user;
        user.dateOfBirth = $scope.day + " " + $scope.month + " " + $scope.year;
        
        PostToServerService.sendToSever(user, reqUrl)
                .then(function (response) {

                    alert(response.data.message);
                    var requestStatus = response.data.HttpStatus;
                    if (requestStatus === "CREATED") {
                        $scope.login("/login");
                    } else {

                    }

                    $scope.goto("/login");
                });
    };
    
    

   $scope.login = function () {
        
        var reqUrl = "/login";
        var user = $scope.user;
        PostToServerService.sendToSever(user, reqUrl)
                .then(function (response) {

                    alert(response.data.message);
                    var requestStatus = response.data.HttpStatus;

                    if (requestStatus === "FOUND") {
                        
                        var user = response.data.userIn;
                        var sessionID = response.data.sessionID;
                        sessionStorage.setItem("userIn", JSON.stringify(user));
                        sessionStorage.setItem("sessionID", sessionID);

                        if (user !== null)
                        {
                            var title;
                            if (user.gender === "Male") {

                                title = "Hi Mr. ";
                            } else {
                                title = "Hi Ms. ";
                            }
                            alert(title + " " + user.lastname) ;
                            $rootScope.loginStatus = title + " " + user.lastname;
                            sessionStorage.setItem("loginStatus", $rootScope.loginStatus);
                        }
                    }
                    
                    if(sessionStorage.getItem("currentUrl") !== null && sessionStorage.getItem("sessionID") !== null){

                        var url = sessionStorage.getItem("currentUrl");
                                  sessionStorage.removeItem("currentUrl");
                        $scope.goto(url);
                    }else{
                        
                        $scope.goto(response.data.url);
                    }

                    
                });
    };
    
     $scope.logout = function (url) {

        
        UserGetService.sendGetRequest(url+ "/" + sessionStorage.getItem("sessionID")).then(function (response) {
            var status = response.data.status;
            if (status === "OK") {
                
                sessionStorage.removeItem("loginStatus");
                $rootScope.loginStatus = null;
                sessionStorage.removeItem("userIn");
                sessionStorage.removeItem("sessionID");
               alert("Logging out")    ;
            }
        });
         
        $scope.goto("/");
    };
    
    $scope.updateUser = function () {

        var user = $rootScope.inUser;
        
        user.dateOfBirth = $scope.inUser.day + " " + $scope.inUser.month + " " + $scope.inUser.year;

        var requestData = {
            newData: user,
            sessionID: sessionStorage.getItem("sessionID")
        };

        if (sessionStorage.getItem("sessionID") !== null && sessionStorage.getItem("userIn") !== null) {

            PostToServerService.sendToSever(requestData, "/updateProfile").then(function (response) {
                
                alert(response.data.message);
                
                    var requestStatus = response.data.HttpStatus;

                    if (requestStatus === "FOUND") {

                        var user = response.data.userIn;
//                        var sessionID = response.data.sessionID;
                        sessionStorage.setItem("userIn", JSON.stringify(user));
//                        sessionStorage.setItem("sessionID", sessionID);
                        if (user !== null)
                        {
                            var title;
                            if (user.gender === "Male") {

                                title = "Hi Mr. ";
                            } else {
                                title = "Hi Ms. ";
                            }

                            $rootScope.loginStatus = title + " " + user.lastname;
                            sessionStorage.setItem("loginStatus", $rootScope.loginStatus);
                        }
                    }
                        
            });
            
            $rootScope.inUser = {};
            
            $scope.goto("/account");
        } 
    };


    $scope.cart = [];
    $scope.display = true;
    $scope.showcart = function () {
    $scope.display = $scope.display ? false : true;
    };

    $scope.addToCart = function (product) {
        alert("Trying to add to cart");



        $scope.cart.push(product);
        


    };
    
    $scope.checkout = function () {

       
        alert($scope.cart);
        // var user = localStorage.getItem("userIn");
        //alert("$scope.cart");
        var orderData = {

            orderItems: $scope.cart,
            destinationInfo: $scope.destination,
            addressInfo: $scope.address,
            user: sessionStorage.getItem("userIn"),
            sessionID:sessionStorage.getItem("sessionID")

        };
         

        if (sessionStorage.getItem("sessionID") !== null){
            
            PostToServerService.sendToSever(orderData,"/checkout").then(function (response) {

                alert(response.data.message);
               
                if(response.data.status === "CREATED"){
                 
                   
                    localStorage.clear();
                    
                }

            });
            
        //    $scope.cart = [];
         //   alert("Showing cart length") ;
        //    alert($scope.cart.length);
            $scope.goto("/thankyou");

        } else {
           
            sessionStorage.setItem("currentUrl", "/cart");
            $scope.goto("/login");
        }

    };
    
    
 $scope.addQuantity = function (id) {



        for (var i = 0, max = $scope.cart.length; i < max; i++) {

            if ($scope.cart[i].id === id) {

                $scope.cart[i].quantity = $scope.cart[i].quantity + 1;

            }
        }
        
     

        $scope.calcTotal($scope.cart);


        
    };

    $scope.subtractQuantity = function (id) {

        for (var i = 0, max = $scope.cart.length; i < max; i++) {

            if ($scope.cart[i].id === id) {

                if ($scope.cart[i].quantity > 1) {
                    $scope.cart[i].quantity = $scope.cart[i].quantity - 1;
                }

            }
        }
        
       

        $scope.calcTotal($scope.cart);


      
    };
    
    
    
    
     $scope.removeProduct = function(id){
               $http.post("/edit_deleteproduct" , id).then(function(response){                 
                   alert("Are you sure you want to delete product?");
                   if(response.data.status ==="OK")
                   {
                       alert(response.data.message);
                        $rootScope.goto("/admin");
                   }
               });
           };
    
     $scope.addAndRemove = function (selected, prod) {
        var index = selected.indexOf(prod);

        if (index > -1) {

            selected.splice(index, 1);
        } else {

            selected.push(prod);
        }
    };
    
     $scope.calcTotal = function (cartProducts) {

        $scope.total = 0;

        for (var i = 0; i < cartProducts.length; i++) {

            $scope.total = $scope.total + cartProducts[i].quantity * cartProducts[i].price;

        }
        return $scope.total ;

    };
    
     $scope.removeSelected = function (selected) {

       
        
        var cartListSize = $scope.cart.length;

        for (var c = 0; c < cartListSize; c++) {

            var isFound = false;
            var indexS = -1;
            var indexC = -1;
            var selectedListSize = selected.length;

            for (var i = 0; i < selectedListSize; i++) {

                if ($scope.cart[c].id === selected[i].id) {
                    isFound = true;
                    indexS = i;
                    indexC = c;
                    i = selectedListSize + 100;
                }
            }

            if (isFound) {

                $scope.cart.splice(indexC, 1);
                selected.splice(indexS, 1);
                cartListSize = $scope.cart.length;
                selectedListSize = selected.length;
                c = c - 1;
            }

        }

      
        $scope.calcTotal($scope.cart);
        
        

    };


    $http.get("/customers").then(function (response) {
        $scope.customers = response.data;
    });
  
    $http.get("/product").then(function (response) {
        $scope.products = response.data;
    });
    
     $http.get("/order").then(function (response) {
        $scope.orders = response.data;
    });

});


myApp.controller("adminController", function ($scope, $http, $location, $rootScope) {
    alert("adminController");
    $rootScope.goto = function (url) {
        $location.path(url);
    };



});


//--------------------------------------------App Services------------------------------------------
myApp.factory("PostToServerService", ["$http", function ($http) {

        var serviceInstance = {};
        serviceInstance.sendToSever = function (data, reqUrl) {

            return $http.post(reqUrl, data);
        };
        return serviceInstance;
    }]);
myApp.factory("UserGetService", ["$http", function ($http) {

        var serviceInstance = {};
        serviceInstance.sendGetRequest = function (requestUrl) {

            return $http({
                url: requestUrl ,
                method: "GET"

            });
        };
        return serviceInstance;
    }]);
myApp.factory("OrderPostService", ["$http", function ($http) {

        var serviceInstance = {};
        serviceInstance.completeOrder = function (productsData, requestUrl) {

            return $http.post(requestUrl, productsData);
        };
        return serviceInstance;
    }]);
myApp.factory("ProductGetService", ["$http", function ($http) {

        var serviceInstance = {};
        serviceInstance.getProductsFromServer = function (requestUrl) {

            return $http({
                url: requestUrl,
                method: "GET"

            });
        };
        return serviceInstance;
    }]);