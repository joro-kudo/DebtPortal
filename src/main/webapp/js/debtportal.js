/**
 * view-controller for debtshelf.html
 * @author Marcel Suter
 */
document.addEventListener("DOMContentLoaded", () => {
    readDebts();
});

/**
 * reads all debts
 */
function readDebts() {
    fetch("./resource/debt/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showDebtlist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the debtlist as a table
 * @param data  the debts
 */
function showDebtlist(data) {
    let tBody = document.getElementById("debtlist");
    data.forEach(debt => {
        let row = tBody.insertRow(-1);
        row.insertCell(-1).innerHTML = debt.message;
        row.insertCell(-1).innerHTML = debt.person.personName;
        row.insertCell(-1).innerHTML = debt.price;

        let button = document.createElement("button");
        button.innerHTML = "Bearbeiten ...";
        button.type = "button";
        button.name = "editDebt";
        button.setAttribute("data-debtuuid", debt.debtUUID);
        button.addEventListener("click", editDebt);
        row.insertCell(-1).appendChild(button);

        button = document.createElement("button");
        button.innerHTML = "Löschen ...";
        button.type = "button";
        button.name = "deleteDebt";
        button.setAttribute("data-debtuuid", debt.debtUUID);
        button.addEventListener("click", deleteDebt);
        row.insertCell(-1).appendChild(button);

    });
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editDebt(event) {
    const button = event.target;
    const debtUUID = button.getAttribute("data-debtuuid");
    window.location.href = "./debtedit.html?uuid=" + debtUUID;
}

/**
 * deletes a debt
 * @param event  the click-event
 */
function deleteDebt(event) {
    const button = event.target;
    const debtUUID = button.getAttribute("data-debtuuid");

    fetch("./resource/debt/delete?uuid=" + debtUUID,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./debtshelf.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}