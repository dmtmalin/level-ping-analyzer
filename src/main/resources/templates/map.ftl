<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru" lang="ru">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="content-type" content="text/html; charset=windows-1251" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Home page</title>
    <link rel="stylesheet" href="app/css/theme.css" />
    <link rel="stylesheet" href="bootstrap-3.3.5-dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="OpenLayers-3.11.1-dist/ol.css" type="text/css" />
    <script src="jquery/jquery-2.1.4.min.js"></script>
    <script src="moment/moment.js"></script>
    <script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <script src="bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script src="OpenLayers-3.11.1-dist/ol.js" type="text/javascript"></script>
</head>
<body>
<div class="container">
    <ul class="nav nav-tabs">
        <li id="tab-map" role="presentation" class="active">
            <a data-toggle="tab" href="#map-content">Карта</a>
        </li>
        <li role="presentation">
            <a data-toggle="tab" href="#settings">Настройки</a>
        </li>
    </ul>
    <div class="tab-content">
        <div id="map-content" class="tab-pane fade in active">
            <div id="map" class="map"></div>
            <div id="popup" class="ol-popup">
                <a href="#" id="popup-closer" class="ol-popup-closer"></a>
                <div id="popup-content"></div>
            </div>
        </div>
        <div id="settings" class="content-container tab-pane fade">
            <h3>Общие</h3>
            <form id="form-settings" role="form" action="">
                <div class="form-group">
                    <label for="sector-size">Размер сектора, м</label>
                    <select class="form-control" id="sector-size">
                        <option value='22'>10</option>
                        <option value='21'>20</option>
                        <option value='20'>50</option>
                        <option value='19'>100</option>
                        <option value='18'>150</option>
                        <option value='17'>350</option>
                        <option value='16'>700</option>
                        <option value='15'>1400</option>
                        <option selected value='14'>2800</option>
                        <option value='13'>5500</option>
                        <option value='12'>10000</option>
                        <option value='11'>22000</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="ping">Порог задержки, с</label>
                    <input id="ping" class="form-control" type="number" value="1" min="1" />
                </div>
                <label for='start_time'>Интервал</label>
                <div class='input-group date' id='start_time'>
                    <input type='text' class="form-control" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-time"></span>
					</span>
                </div>
                <div class='input-group date' id='end_time'>
                    <input type='text' class="form-control" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-time"></span>
					</span>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="app/js/main.js"></script>
</body>
</html>