window.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form'),
        testTypeSelect = document.querySelector('form select');

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const checkboxes = document.querySelectorAll('input[type=checkbox]:checked');
        let boxes = [];
        checkboxes.forEach(box => {
            boxes.push(box.value);
        });

        sessionStorage.setItem('type', testTypeSelect.value);
        sessionStorage.setItem('categories', JSON.stringify(boxes));
        sessionStorage.setItem('startTime', JSON.stringify(new Date()));

        form.submit();
    });
});