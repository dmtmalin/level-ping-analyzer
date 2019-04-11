//добавление кнопки на карту
function clearMapControl (opt_options) {

  var options = opt_options || {};

  var button = document.createElement('button');
  button.innerHTML = '&#8635'; //arrow

  var handleClearMap = function(e) {
    map.getLayers().forEach(function(layer) {
        var source = layer.getSource();
        if(source instanceof ol.source.Vector) source.clear();

    });
  };

  button.addEventListener('click', handleClearMap, false);
  button.addEventListener('touchstart', handleClearMap, false);

  var div = document.createElement('div');
  div.className = 'btn-clear ol-unselectable ol-control';
  div.appendChild(button);

  ol.control.Control.call(this, {
    element: div,
    target: options.target
  });

};
ol.inherits(clearMapControl, ol.control.Control);

//карта
var map = new ol.Map({
    controls: ol.control.defaults({
        attributionOptions: /** @type {olx.control.AttributionOptions} */ ({
            collapsible: false
        })
    }).extend([
        new clearMapControl()
    ]),
    interactions: ol.interaction.defaults({ shiftDragZoom:false }),
    target: document.getElementById('map'),
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    view: new ol.View({
        center: ol.proj.fromLonLat([37.609218,55.753559]), //центр Москвы
        zoom: 10
    })
});

$( document ).ready(function() {
    $('[href=#settings]').tab('show');
});

// a DragBox interaction used to select features by drawing boxes
var dragBox = new ol.interaction.DragBox({
    condition: ol.events.condition.shiftKeyOnly
});

map.addInteraction(dragBox);

dragBox.on('boxend', function(e) {
    map.removeInteraction(dragBox);
    var box = dragBox.getGeometry().transform('EPSG:3857', 'EPSG:4326').getCoordinates()[0];
    var accuracy = $('#sector-size').val();
    buildZones(box, accuracy);
});

function buildZones (box, accuracy) {
    var start = getTime('#start_time');
    var end = getTime('#end_time');
    var ping = $('#ping').val();
    var data = JSON.stringify({
        box: box, accuracy: accuracy, start: start, end: end,  ping: ping
    });
    $.ajax({
        url: '/zones', type: 'POST', data: data, contentType: 'application/json; charset=utf-8', dataType: 'json'
    }).done(function(json) {
        console.log("/zones len response: " + json.length);
        if(json.length > 0)
            draw(json, accuracy);
        else
            alert ('Нет данных для отображения');
    }).always(function() {
        console.log("Map interaction");
        map.addInteraction(dragBox);
    });
}

function draw(data, accuracy) {
    var vectorBox = new ol.source.Vector({});

    for(var i=0; i < data.length; i++) {
        var box = data[i]['box'];
        var polygon  =  new ol.geom.Polygon([box]).transform('EPSG:4326', 'EPSG:3857');

        var style = new ol.style.Style({
            fill: new ol.style.Fill({ color: data[i]['color'] }),
            stroke: new ol.style.Stroke({ color: '#000', width: 0.8 })
        });

        var content = buildContent(data[i]['ping'], data[i]['numbers'], data[i]['interval']);
        var feature = new ol.Feature({
            geometry: polygon,
            accuracy: accuracy,
            info: content
        });

        feature.setStyle(style);
        vectorBox.addFeature(feature);
    }
    var vectorBoxLayer = new ol.layer.Vector({
        source: vectorBox,
        opacity: 0.4
    });
    map.addLayer(vectorBoxLayer);
}

//раскрытие зоны
map.on('dblclick', function(evt) {
    var feature = map.forEachFeatureAtPixel(evt.pixel,
    function(feature, layer) {
        if(feature) layer.getSource().removeFeature(feature);
        return feature;
    })
    if (feature) {
        var box = feature.getGeometry().transform('EPSG:3857', 'EPSG:4326').getCoordinates()[0];
        var accuracy = parseInt(feature.get('accuracy')) + 1;
        buildZones(box, accuracy);
    }
});

function buildContent (ping, numbers, interval) {
	var content = "<p>Задержка, сек: ";
	content += ping.toFixed(2);
	content += "<br>Количество точек: ";
	content += numbers;
	content += "<br>Интервал загрузки точек:<br>";
	content += interval['start'];
	content += "<br>";
	content += interval['end'];
	content += "</p>";
	return content;
}

//построение подсказки
var popup = new ol.Overlay({
    element: $('#popup')
});
map.addOverlay(popup);

function popupDestroy() {
    popup.setPosition(undefined);
    $('#popup-closer').blur();
}

$('#popup-closer').click(function() {
    popupDestroy();
    return false;
});

//показываем подсказку
map.on('singleclick', function(evt) {
    var feature = map.forEachFeatureAtPixel(evt.pixel,
        function(feature, layer) {
            return feature;
        });
    if (feature) {
        $('#popup-content').html(feature.get('info'));
        popup.setPosition(evt.coordinate);
    }
});

// убираем popup при сдвиге курсора и изменяем вид курсора
map.on('pointermove', function(e) {
    var pixel = map.getEventPixel(e.originalEvent);
    var hit = map.hasFeatureAtPixel(pixel);
    map.getTarget().style.cursor = hit ? 'pointer' : '';
    if(!hit) popupDestroy();
});

//datetimepickers
function getTime(name) {
	return $(name).data('DateTimePicker').date().format('YYYY-MM-DD HH:mm:ss');
}

$(function () {
	$('#start_time').datetimepicker({
		format: "DD.MM.YYYY HH:mm:ss",
		defaultDate: moment().format('YYYY-MM-DD') + ' 00:00:00'
	});
});

$(function () {
	$('#end_time').datetimepicker({
		format: "DD.MM.YYYY HH:mm:ss",
		defaultDate: moment().format('YYYY-MM-DD') + ' 23:59:59'
	});
});