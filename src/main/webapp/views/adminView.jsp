<!DOCTYPE html>
<html lang="en">

<head>
  <title>Page Title</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <style>
    body {
      font-family: Arial;
      margin: 0;
      background-color: #afcf20;
    }

    .header {
      padding: 0.005%;
      text-align: center;
      background: #1abc9c;
      color: white;
      font-size: 20px;

    }
  </style>
</head>

<body>

  <div class="header">
    <h1>Admin Dashboard view</h1>
  </div>
  <div style="padding-left: 4%;">
    <div>
      <p>Players who made the payments</p>
      <form action="/paymentUpdate" method="post">
        <input type="text" name="regid" placeholder="Enter Reg ID like 1,2,3" required>
        <button type="submit">Submit</button>
      </form>
    </div>
    <div>
      <p>Players to be updated in Category A</p>
      <form action="/updateCategory" method="post">
        <input type="text" name="regid" placeholder="Enter Reg ID like 1,2,3" required>
        <button type="submit">Submit</button>
      </form>
    </div>
    <div>
      <p>All document pdf download based on registration ID</p>
    <form action="/downloadDocImg" method="GET">
        <button type="submit">Download all document</button>
    </form>
    </div>
    <div class="select-box">
      <p>Player category specific photo download</p>
      <form action="/downloadCategorySpecificImage" method="post">
      <select name="playerCategory">
        <option hidden>Player Category</option>
        <option value="Batsman">Batsman</option>
        <option value="Bowler">Bowler</option>
        <option value="Wicket Keeper">Wicket Keeper</option>
        <option value="All Rounder">All Rounder</option>
        <option value="List A">List A</option>
      </select>
      <button type="submit">Download category specific image</button>
    </form>
    </div>
    <div>
      <p>All Player PDF</p>
    <form action="/kpl/registration/api/generate/AllplayerPdf" method="get" target="_blank">
        <button type="submit">Download All Player Pdf</button>
    </form>
    </div>
    <div class="select-box">
      <p>Player category specific pdf for owners</p>
      <form action="/kpl/registration/api/generate/finalPlayerListPdf" method="get" target="_blank">
      <select name="generue">
        <option hidden>Player Category</option>
        <option value="Batsman">Batsman</option>
        <option value="Bowler">Bowler</option>
        <option value="Wicket Keeper">Wicket Keeper</option>
        <option value="All Rounder">All Rounder</option>
        <option value="List A">List A</option>
      </select>
      <button type="submit">Download category specific pdf</button>
    </form>
    </div>
    <div class="select-box">
      <p>Player category specific pdf for committe</p>
      <form action="/kpl/registration/api/generate/playerPdf" method="get" target="_blank">
      <select name="generue">
        <option hidden>Player Category</option>
        <option value="Batsman">Batsman</option>
        <option value="Bowler">Bowler</option>
        <option value="Wicket Keeper">Wicket Keeper</option>
        <option value="All Rounder">All Rounder</option>
        <option value="List A">List A</option>
      </select>
      <button type="submit">Download category specific pdf</button>
    </form>
    </div>
  </div>
</body>

</html>