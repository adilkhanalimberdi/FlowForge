import type {Card} from "../../types/card/Card.ts";
import CardComponent from "../card/CardComponent.tsx";
import {type Dispatch, type SetStateAction, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";


interface Props {
    card: Card;
    setCanSave: Dispatch<SetStateAction<boolean>>;
    setSaving: Dispatch<SetStateAction<boolean>>;
    saving: boolean;
}


function WorkspacePage({ card, setCanSave, saving, setSaving }: Props) {
    const [after, setAfter] = useState(structuredClone(card));
    const [cardClicked, setCardClicked] = useState<boolean>(false);
    const navigate = useNavigate();

    useEffect(() => {
        if (saving) {
            setSaving(false);
        }
    }, [saving]);

    useEffect(() => {
        if (cardClicked) {
            navigate(`card/${card.id}`);
            setCardClicked(false);
        }
    }, [cardClicked]);

    return (
            <div className={"w-[320px] min-h-[400px] rounded-2xl mb-4 drop-shadow-xl hover:drop-shadow-2xl shadow-black hover:cursor-pointer " +
                ((card.status) === "NOT_STARTED" ? "bg-blue-100" : ((card.status) === "IN_PROGRESS" ? "bg-yellow-100" : "bg-green-100"))}>

                <CardComponent before={card} after={after} setCanSave={setCanSave} saving={saving} setSaving={setSaving} setAfter={setAfter} setCardClicked={setCardClicked} />

            </div>
    )

}

export default WorkspacePage;