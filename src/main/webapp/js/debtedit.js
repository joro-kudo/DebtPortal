/**
 * view-controller for debtedit.html
 * @author Marcel Suter
 */
document.addEventListener("DOMContentLoaded", () => {
    readPeople();
    readDebt();
    document.getElementById("debteditForm").addEventListener("submit", saveDebt);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
    document.getElementById("reset").addEventListener("click", resetEdit);

});

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

    if (debtUUID == null) {


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
        .then(response => {
            response.json();
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

    document.getElementById("description").value = data.description;
    document.getElementById("price").value = data.price;
    document.getElementById("debitor").value = data.debitorUUID;
    document.getElementById("creditor").value = data.creditorUUID;

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
    let dropdown = document.getElementById("debitor");
    let dropdown2 = document.getElementById("creditor");


    data.forEach(debitor => {
        let option = document.createElement("option");
        option.text = debitor.personName;
        option.value = debitor.personUUID;
        dropdown.add(option);

    })
    data.forEach(creditor => {
        let option = document.createElement("option");
        option.text = creditor.personName;
        option.value = creditor.personUUID;
        dropdown2.add(option);

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
    for (var i = 0; i < fieldsToReset.length; i++) {
        fieldsToReset[i].value = null;
    }
}
