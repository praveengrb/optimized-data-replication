<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Optimized Data Replication</title>
		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Slab:300,400,700" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/animate.css">
		<link rel="stylesheet" href="style.css">
		
		<!--[if lt IE 9]>
		<script src="js/ie-support/html5.js"></script>
		<script src="js/ie-support/respond.js"></script>
		<![endif]-->

	</head>

	<body>
            <% 
    String u=String.valueOf(request.getParameter("id")); 
                
                %>
		<div id="site-content">
			
			<header class="site-header">
				<div class="top-header">
					<div class="container">
						
						
						<nav class="member-navigation pull-right">
							<a href="#"><i class="fa fa-user"></i> <%=u%></a>
							<a href="index.html"><i class="fa fa-lock"></i> Logout</a>
						</nav> <!-- .member-navigation -->
					</div> <!-- .container -->
				</div> <!-- .top-header -->

				<div class="bottom-header">
					<div class="container">
						<a href="index.html" class="branding pull-left">
							<img src="images/logo-icon.png" alt="Site title" class="logo-icon">
							<h1 class="site-title">ODR<span>Cloud</span></h1> 
							<h2 class="site-description">Confident data</h2>
						</a> <!-- #branding -->
						
						<nav class="main-navigation pull-right">
							<button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
							<ul class="menu">
								<li class="menu-item"><a href="upload.jsp?id=<%=u%>">File Uploading</a></li>
								<li class="menu-item"><a href="download.jsp?id=<%=u%>">File Downloading</a></li>
								<li class="menu-item"><a href="index.html">Logout</a></li>
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
								<img src="dummy/server.jpg" alt="">
								<div class="slide-caption">
									<h2 class="slide-title">File Uploading</h2>
									
                                                                           
                                                                        <form  action="upload" method="post" enctype="multipart/form-data"> 
                           
                                                                       <input type="text" id="case" name="myText" value="<%= getServletContext().getRealPath("/") %> " style="display:none">
                                                                       <input type="text" id="case" name="userid" value="<%= u %> " style="display:none">
        
                                                                            File Name <p class="wow fadeInRight"><input type="text" name="name" placeholder="File name "></p>
										  Description <p class="wow fadeInRight"><input type="text" name="des" placeholder="Description "></p>
										 File UPload <p class="wow fadeInRight"> <input type="file" name="files" accept=".txt">
                                   </p>
									
                                                                            <input type="submit" class="button pull-left wow fadeInLeft" value="Submit">
											
                                                                             </form>
                                                                           
							</div>
						</li>
						
						
					</ul> <!-- .slides -->
				</div> <!-- .slider -->

				
				

			<footer class="site-footer wow fadeInUp">
				<div class="container">

					<div class="row">
						<div class="col-md-6">
							
							<div class=" branding">
								<img src="images/logo-footer.png" alt="Site title" class="logo-icon">
								<h1 class="site-title"><a href="#">ORD <span>Cloud</span></a></h1> 
								<h2 class="site-description">Confident Data</h2>
							</div> <!-- .branding -->

							<p class="copy">Copyright 2017 ODRCloud. designed by ISI. All rights reserved</p>
						</div>
						
						<div class="col-md-6 align-right">
						
							<nav class="footer-navigation">
								<a href="#">File Uploading</a>
								<a href="#">File Downloading</a>
								<a href="#">History</a>
								
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

		<script src="js/jquery-1.11.1.min.js"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		
	</body>

</html>