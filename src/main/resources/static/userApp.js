async function showUser() {
    fetch("http://localhost:8080/api/viewUser")
        .then(res => res.json())
        .then(user => {

            //Заголовок. Почта, роли, логаут. Для авторизованного пользователя
            $("#headerUsername").append(user.username)
            let roles = user.roleSet.map(role => " " + role.shortRole)
            $("#headerRoles").append(roles)

            // Таблица для юзера
            $("#tbody").append(`
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.username}</td>
                <td>${roles}</td>
            </tr>
            `)
        })
}

$(async function() {
    await showUser();
});


alert("asdfdfhdh")

console.log("!!!!!!!!!!!!!!!!!!!")


