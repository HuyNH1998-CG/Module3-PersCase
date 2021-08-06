<%@ page import="Model.KhachHang" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Electro - HTML Ecommerce Template</title>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/slick.css"/>
    <link type="text/css" rel="stylesheet" href="css/slick-theme.css"/>
    <link type="text/css" rel="stylesheet" href="css/nouislider.min.css"/>
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>
<body>
<header>
    <div id="top-header">
        <div class="container">
            <ul class="header-links pull-left">
            </ul>
            <ul class="header-links pull-right">
                <%
                    KhachHang user = (KhachHang) request.getSession().getAttribute("user");
                    if (user == null) {
                %>
                <li><a href="/login"><i class="fa fa-user-o"></i> My Account</a><li>
                    <%
                } else {
                %>
                <li><a href="/user"><i class="fa fa-user-secret"></i> My Account</a></li>
                <li><a href=""><i class="fa fa-user-o"></i>${user.name}</a></li>
                <li><a href="/login?action=logout">Log out</a><li>
                    <%
                    }
                %>
            </ul>
        </div>
    </div>
    <!-- /TOP HEADER -->

    <!-- MAIN HEADER -->
    <div id="header">
        <!-- container -->
        <div class="container">
            <!-- row -->
            <div class="row">
                <!-- LOGO -->
                <div class="col-md-3">
                    <div class="header-logo">
                        <a href="/" class="logo">
                            <img src="./img/logo.png" alt="">
                        </a>
                    </div>
                </div>
                <!-- /LOGO -->

                <!-- SEARCH BAR -->
                <div class="col-md-6">
                    <div class="header-search">
                        <form action="/?action=search2">
                            <select class="input-select" name="id">
                                <option value="0">All Categories</option>
                                <c:forEach var="ctg" items="${category}">
                                    <option value="${ctg.id}">${ctg.name}</option>
                                </c:forEach>
                            </select>
                            <input class="input" name="name" placeholder="Search here">
                            <button type="submit" class="search-btn">Search</button>
                        </form>
                    </div>
                </div>
                <!-- /SEARCH BAR -->
            </div>
            <!-- row -->
        </div>
        <!-- container -->
    </div>
    <!-- /MAIN HEADER -->
</header>
<!-- /HEADER -->

<!-- NAVIGATION -->
<nav id="navigation">
    <!-- container -->
    <div class="container">
        <!-- responsive-nav -->
        <div id="responsive-nav">
            <!-- NAV -->
            <ul class="main-nav nav navbar-nav">
                <li class="active"><a href="/">Home</a></li>
                <li><a href="/?action=createPrd">Create Product</a></li>
                <li><a href="/register.jsp">Create Access Account</a></li>
                <li><a href="/?action=createCat">Create Category</a></li>
                <li><a href="/order?action=view">Check Order</a></li>
                <li><a href="/user">Check Accounts</a></li>
            </ul>
            <!-- /NAV -->
        </div>
        <!-- /responsive-nav -->
    </div>
    <!-- /container -->
</nav>
<!-- /NAVIGATION -->

<!-- SECTION -->
<div class="section">
    <!-- container -->
    <div class="container">
        <!-- row -->
        <div class="row">
            <!-- section title -->
            <div class="col-md-12">
                <div class="section-title">
                    <h3 class="title">Products</h3>
                </div>
            </div>
            <!-- /section title -->
            <!-- Products tab & slick -->
            <div class="col-md-12">
                <div class="row">
                    <!-- tab -->
                    <c:forEach var="prd" items="${product}">
                        <div class="col-md-4 col-xs-6">
                            <div class="product">
                                <div class="product-img">
                                    <img src="${prd.hinhanh}" alt="">
                                    <div class="product-label">
                                        <span class="sale"></span>
                                        <span class="new">NEW</span>
                                    </div>
                                </div>
                                <div class="product-body">
                                    <p class="product-category">${prd.phanloai}</p>
                                    <h3 class="product-name"><a href="#">${prd.ten}</a></h3>
                                    <h4 class="product-price">${prd.gia}</h4>
                                    <div class="product-btns">
                                        <a href="/?action=editPrd&id=${prd.id}" class="quick-view"><i
                                                class="fa fa-eye"></i><span
                                                class="tooltipp">Edit</span></a>
                                    </div>
                                </div>
                                <div class="add-to-cart">
                                    <a href="/?action=deletePrd&id=${prd.id}" class="add-to-cart-btn"><i
                                            class="fa fa-trash"></i> Delete </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <!-- Products tab & slick -->
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /SECTION -->
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-title">
                            <h3 class="title">Categories</h3>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table>
                            <c:forEach var="ctg" items="${category}">
                                <tr>
                                    <th colspan="4">${ctg.name}</th>
                                    <td><a href="/?action=editCat&id=${ctg.id}" class="btn btn-danger">Edit</a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- jQuery Plugins -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slick.min.js"></script>
<script src="js/nouislider.min.js"></script>
<script src="js/jquery.zoom.min.js"></script>
<script src="js/main.js"></script>

</body>
</html>
