/**
 * view-controller for debtedit.html
 * @author Marcel Suter
 */
document.addEventListener("DOMContentLoaded", () => {
    readPeople();
    readDebt();
    document.getElementById("debtUUID").value = uuidv4()
    document.getElementById("debteditForm").addEventListener("submit", saveDebt);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
    document.getElementById("reset").addEventListener("click", resetEdit);

});
/**
 generates a uuid
 */
function uuidv4() {
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
        (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
}
/**
 * saves the data of a debt
 */
function saveDebt(event) {
    event.preventDefault();

    const debtForm = document.getElementById("debteditForm");
    const formData = new FormData(debtForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/debt/";
    let debtUUID = getQueryParam("uuid");

    if (debtUUID ==null){


        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }

    fetch(url,
        {
            method: method,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                console.log(response);
            } else return response;
        })
        .then()
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a debt
 */
function readDebt() {
    let debtUUID = getQueryParam("uuid");

    fetch("./resource/debt/read?uuid=" + debtUUID)
        .then(function (response) {
            console.log("1");

            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => {response.json();
})
.then(data => {
            showDebt(data);


        })
        .catch(function (error) {

        });
}

/**
 * show the data of a debt
 * @param data  the debt-data
 */
function showDebt(data) {
    document.getElementById("debtUUID").value = data.debtUUID;
    document.getElementById("description").value = data.description;
    document.getElementById("price").value = data.price;
    document.getElementById("person").value = data.personUUID;

}

/**
 * reads all people as an array
 */
function readPeople() {

    fetch("./resource/person/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showPeople(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all people as a dropdown
 * @param data
 */
function showPeople(data) {
    let dropdown = document.getElementById("person");
    data.forEach(person => {
        let option = document.createElement("option");
        option.text = person.personName;
        option.value = person.personUUID;
        dropdown.add(option);
    })
}

/**
 * redirects to the debtportal
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./debtportal.html";
}
/**
 * generates new uuid
 * * @param event  the click-event
 */
function resetEdit(event) {
    var fieldsToReset = document.querySelectorAll("input:not([data-noreset='true'])")
    for(var i=0;i<fieldsToReset.length;i++){
        fieldsToReset[i].value = null;
    }
    document.getElementById("debtUUID").value = uuidv4();
}
