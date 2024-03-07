
function dateDifference(date1, date2){

    var d1 = Date.parse(date1);
    var d2 = Date.parse(date2);

    var diff = Math.abs(d1 - d2);

    // Convert the difference to days and return it
    return diff / (1000 * 60 * 60 * 24);
}

export {dateDifference}