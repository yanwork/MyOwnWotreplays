function FieldFacet(id, facetData) {
    this.id = id;
    $.extend(this, facetData);
    var facetContainer = document.getElementById(this.id);
    facetContainer.innerHTML = this.template(this);
}

FieldFacet.prototype.template = Handlebars.compile($('#fieldFacetTemplate').html());

FieldFacet.prototype.getQueryParam = function () {
    var result = [];
    for (var value in this.values)
        if ($('#' + this.id + ' #' + value).prop('checked'))
            result.push(value);
    if (result.length > 0)
        return this.id + '=' + result.join(',');
    else
        return null;
};

FieldFacet.prototype.setSelected = function (queryParams) {
    var selectedValues = queryParams[this.id] ? queryParams[this.id] : [];
    for (var value in this.values)
        $('#' + this.id + ' #' + value).prop('checked', selectedValues.indexOf(value) != -1);
};

FieldFacet.prototype.setResult = function (data) {
    for (var value in this.values) {
        var count = data[value];
        var label = $('#' + this.id + ' label[for=' + value + ']');
        if (count) {
            label.find('small').html('(' + data[value] + ')');
            label.removeClass('disabled');
        } else
            label.addClass('disabled');
    }
};