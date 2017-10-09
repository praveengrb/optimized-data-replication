<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.mysql.jdbc.Connection"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Optimized Data Replication</title>
                   <link href="css/bootstrap.css" rel="stylesheet" />
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
 <style type="text/css">
         .file {
            visibility: hidden;
            position: absolute;
        }
        #loadheading{
            font-family: 'Kurale';font-size: 35px;
        }
        body{
            background-image: url("img/wall.jpg") ;
        }
        .btn-primary{
            background: #101010;
        }
        table{
            width:100%;
        }
        #footer{
             
            bottom:0;
            width:100%;
        }
        .alert-warning{
            background: rgb(66, 131, 244);
            color:rgb(232, 237, 244);
            width:70%;
            font-size:14pt;
        }
        #result{
            display:none;
        }
        tr:nth-child(even) {background-color: #f2f2f2}
        table th{
            background-color: rgb(66, 131, 244);
            text-align: center;
              font-size: 15px;
              color:white;
        }
     </style>
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
							<img src="images/logo-icon.png" alt="Site title" class="logo-icon">
							<h1 class="site-title">ODR<span>Cloud</span></h1> 
							<h2 class="site-description">Confident data</h2>
						</a> <!-- #branding -->
						
						<nav class="main-navigation pull-right">
							<button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
							<ul class="menu">
								<li class="menu-item"><a href="server.jsp">Adding Server</a></li>
								<li class="menu-item"><a href="view.jsp">View cloud server details</a></li>
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
									<h2 class="slide-title">Cloud Storage</h2>
									<small class="slide-subtitle">Optimized Data Replication </small>
									<div class="slide-summary">
                                                                            <p>Optimized Data Replication utilizes the concept of data fragmentation for securing the user data within the cluster. To further enhance the security, the fragments are not stored on the adjacent nodes. To separate the storage of fragments by a certain distance, the concept of T-coloring is used. To improve the retrieval time of fragments, the fragments is stored in the most central nodes.</p>		</div>
									<a href="" class="button">Read More</a>
								</div>
							</div>
						</li>
						<li>
							<div class="container">
								<img src="dummy/server.jpg" alt="">
								<div class="slide-caption">
									<h2 class="slide-title">Data Security</h2>
									<small class="slide-subtitle">NTRU Encryption</small>
									<div class="slide-summary">
                                                                            <p>The files are divided into encrypted fragments and replicated across nodes in the cluster. Each node stores a fragment of a data file and it ensures that in case of successful attacks, no meaningful information is revealed to the hacker. The nodes storing fragments are separated by a certain distance. This prohibits the attacker from guessing the location of the fragments. To handle the download request from the user, a manager collects all the fragments from the nodes and re-assembles them into a single file. Using the decryption algorithms like NTRU the file is decrypted and the file is sent to the user  </p>	</div>
									<a href="" class="button">Read More</a>
								</div>
							</div>
						</li>
						
					</ul> <!-- .slides -->
				</div> <!-- .slider -->
                        </main>
 <center><h2 id="heading">Server Details</h2></center><br/>
         <table class="table table-striped" id="table">
            <thead>
            <tr>
                <th>Server ID</th>
                <th>Distance</th>
                <th>Capacity</th>
                <th>T-Color</th>
              
            </tr>
            </thead>
            <tbody>
                                 
       <%  
           
        
            
                
              try
                             
                               { Class.forName("com.mysql.jdbc.Driver").newInstance();
                 
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/odr", "root", "root");
            String sa="select * from servernode";
                PreparedStatement pr=con.prepareStatement(sa);
		ResultSet rs=pr.executeQuery();
                
		
          
     %>
        
         <%      
while(rs.next()){     
System.out.println(rs.getString(1));     
    %>
        <tr>
          
          

            <td align="center"><%= rs.getString(1)  %></td>
                   
          <td align="center"><%= rs.getString(2) %></td>
                     
          <td align="center"><%= rs.getString(3) %></td>
          
          <td align="center"><%= rs.getString(4) %></td>
           
      </tr>
   
    <%
 } 
                               }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
                         
            %>
           <!-- /.container -->

                    </tbody>
      </table>
      
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
								<a href="#">News</a>
								<a href="#">About us</a>
								<a href="#">Services</a>
								<a href="#">Contact</a>
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