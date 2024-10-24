import { useEffect, useState } from "react";

function Detail() {
  return (
    <div className="detail">
      <ul>
        {Array.from(Array(10).keys()).map((i) =>
        <li key={i}>Item number {i}</li>
        )}
      </ul>
    </div>
  )
}

export default Detail
