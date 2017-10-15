<%-- 
    Document   : login
    Created on : Sep 27, 2017, 5:57:39 PM
    Author     : ISI7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Optimized Data Replication</title>
		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Slab:300,400,700" rel="stylesheet" type="text/css">
		<link href="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<!-- Loading main css file -->
		<link rel="stylesheet" href="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/css/animate.css">
		<link rel="stylesheet" href="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/css/style.css">
		
		<!--[if lt IE 9]>
		<script src="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/js/ie-support/html5.js"></script>
		<script src="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/js/ie-support/respond.js"></script>
		<![endif]-->

	</head>

	<body>
		
		<div id="site-content">
			
			<header class="site-header">
				<div class="top-header">
					<div class="container">
						
						
						<nav class="member-navigation pull-right">
							<a href="register.jsp"><i class="fa fa-user"></i> Register</a>
							<a href="login.jsp"><i class="fa fa-lock"></i> Login</a>
						</nav> <!-- .member-navigation -->
					</div> <!-- .container -->
				</div> <!-- .top-header -->

				<div class="bottom-header">
					<div class="container">
						<a href="index.html" class="branding pull-left">
							<img src="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/images/logo-icon.png" alt="Site title" class="logo-icon">
							<h1 class="site-title">ODR<span>Cloud</span></h1> 
							<h2 class="site-description">Confident data</h2>
						</a> <!-- #branding -->
						
						<nav class="main-navigation pull-right">
							<button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
							<ul class="menu">
								<li class="menu-item"><a href="#">Home</a></li>
								<li class="menu-item"><a href="login.jsp">Admin</a></li>
								<li class="menu-item"><a href="#">User</a></li>
									</ul>
						</nav> <!-- .main-navigation -->
					</div> <!-- .container -->
				</div> <!-- .bottom-header -->
			</header> <!-- .site-header -->

			<main class="content">
				<div class="slider">
					<ul class="slides">
						<li>
							<div class="container">
								<img src="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/images/server.jpg" alt="">
								<div class="slide-caption">
									<h2 class="slide-title">Account Login</h2>
									<div class="slide-summary">
                                                                             <form  action="login1" method="post" > 
                                                                                  <% 
    String u=String.valueOf(request.getAttribute("mess")); 
if(!u.equalsIgnoreCase("null"))
{
 %>
 <p> <%=u%></p>
 <%
}


%>
                                                                          		Email Address <p class="wow fadeInRight"><input type="email" name="email" placeholder="Email id..."></p>
												Password <p class="wow fadeInRight"><input type="password" name="pass" placeholder="password..."></p>
                                                                            
                                                                            <input type="submit" class="button pull-left wow fadeInLeft" value="Submit">
											    </form>
								</div>
							</div>
						</li>
						
						
					</ul> <!-- .slides -->
				</div> <!-- .slider -->

				

			<footer class="site-footer wow fadeInUp">
				<div class="container">

					<div class="row">
						<div class="col-md-6">
							
							<div class=" branding">
								<img src="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/images/logo-footer.png" alt="Site title" class="logo-icon">
								<h1 class="site-title"><a href="#">ORD <span>Cloud</span></a></h1> 
								<h2 class="site-description">Confident Data</h2>
							</div> <!-- .branding -->

							<p class="copy">Praveen Sankarasubramanian ( https://www.linkedin.com/in/praveensankarasubramanian/ )</p>
						</div>
						
						<div class="col-md-6 align-right">
						
							<nav class="footer-navigation">
								<a href="#">Home</a>
								<a href="#">Login</a>
								<a href="#">Admin</a>
								
							</nav> <!-- .footer-navigation -->
						
							<div class="social-links">
								<a href="#" class="facebook"><i class="fa fa-facebook"></i></a>
								<a href="#" class="twitter"><i class="fa fa-twitter"></i></a>
								<a href="#" class="google-plus"><i class="fa fa-google-plus"></i></a>
								<a href="#" class="pinterest"><i class="fa fa-pinterest"></i></a>
							</div> <!-- .social-links -->
						
						</div>
					</div>

				</div>
			</footer> <!-- .site-footer -->

		</div> <!-- #site-content -->

		<script src="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/js/jquery-1.11.1.min.js"></script>
		<script src="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/js/plugins.js"></script>
		<script src="https://s3.ap-south-1.amazonaws.com/praveen.odr.static.files/js/app.js"></script>
		
	</body>

</html>