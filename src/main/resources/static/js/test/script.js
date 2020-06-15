window.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form'),
        submitBtn = document.querySelector('.container form button');

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const checkboxes = document.querySelectorAll('input[type=checkbox]:checked');
        console.log(checkboxes);
        let boxes = [];
        checkboxes.forEach(box => {
            boxes.push(box.value);
        });
        formData.append('test', JSON.stringify(boxes));

        const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

        console.log(jsonData);
    });
});