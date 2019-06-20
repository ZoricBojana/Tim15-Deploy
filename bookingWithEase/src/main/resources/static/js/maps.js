function showMap(latitude, longitude)
{	
	$('#map').empty();
	initMap(latitude, longitude);
//	alertify.alert(latitude + " " + longitude);
	$('#map').css('display','block');
	$('#map').css('z-index','2');
	$('.divOpacity').css('opacity', '0.2');

}

function closeMap()
{
	$('#map').css('display','none');
	$('.divOpacity').css('opacity', '1');

}

function initMap(latitude, longitude){ 
    // Creating the map.    
    var myMap = new ymaps.Map("map", {
        // The map center coordinates.
        // Default order: “latitude, longitude”.
        // To not manually determine the map center coordinates,
        // use the Coordinate detection tool.
        center: [latitude, longitude],
        controls: ['zoomControl'],
        // Zoom level. Acceptable values:
        // from 0 (the entire world) to 19.
        zoom: 13
    }, {
        searchControlProvider: 'yandex#search'
    }),
    closeButton = new ymaps.control.Button("Close Map");
    closeButton.events
    .add(
      'press',
      function () {
        closeMap();
      }
    )
    myMap.controls.add(closeButton, {float: 'right'});
    // Creating a content layout.
    MyIconContentLayout = ymaps.templateLayoutFactory.createClass(
        '<div style="color: #FFFFFF; font-weight: bold;">$[properties.iconContent]</div>'
    ),

    myPlacemark = new ymaps.Placemark(myMap.getCenter(), {
        hintContent: 'A custom placemark icon'
    }, {
        /**
         * Options.
         * You must specify this type of layout.
         */
        iconLayout: 'default#image',
        // Custom image for the placemark icon.
        iconImageHref: 'images/placeholder.png',
        // The size of the placemark.
        iconImageSize: [30, 42],
        /**
         * The offset of the upper left corner of the icon relative
         * to its "tail" (the anchor point).
         */
        iconImageOffset: [-5, -38]
    });
    myMap.geoObjects
    .add(myPlacemark);
}