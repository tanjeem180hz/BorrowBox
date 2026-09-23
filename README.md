<!DOCTYPE html>
<html lang="en"><head><meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
<title>BorrowBox: Promo Motion</title>
<style>
:root{--bg:#070a16;--ind:#4f46e5;--ind2:#818cf8;--tint:#c7d2fe;--ink:#e8ecff;--mut:#8b93b8;--ok:#34d399;box-sizing:border-box;padding-top:env(safe-area-inset-top,0px);padding-bottom:env(safe-area-inset-bottom,0px)}
html,body{height:100%;margin:0;background:#05070f;color:var(--ink);font-family:Inter,'Segoe UI',system-ui,sans-serif}
body{display:flex;flex-direction:column;align-items:center;justify-content:center;gap:12px}
#st{position:relative;width:min(100vw,160vh);aspect-ratio:16/9;font-size:min(1vw,1.6vh);overflow:hidden;background:radial-gradient(60em 40em at 75% 20%,#2a2f8f55,transparent),radial-gradient(50em 40em at 10% 90%,#4f46e533,transparent),var(--bg);border-radius:1em}
.sc{display:none;position:absolute;inset:0;align-items:center;justify-content:center;gap:7em;padding:0 8em}
.sc.on{display:flex}
.txt{max-width:44em}
.txt h2{font-size:3.6em;line-height:1.12;margin:0 0 .35em;font-weight:700;letter-spacing:-.02em;opacity:0;animation:up .8s cubic-bezier(.2,.8,.2,1) forwards}
.txt h2:nth-child(2){color:var(--ind2);animation-delay:.9s}
.txt h2:nth-child(3){animation-delay:1.8s}
.ctr{flex-direction:column;text-align:center;gap:1.4em}
.ctr .txt{max-width:70em}
@keyframes up{from{opacity:0;transform:translateY(2.5em)}to{opacity:1;transform:none}}
@keyframes pop{from{opacity:0;transform:scale(.85)}to{opacity:1;transform:none}}
@keyframes fl{50%{transform:translateY(-1em)}}
.ph{width:17em;height:34em;border-radius:3em;background:#0d1224;border:.35em solid #1e2547;box-shadow:0 3em 6em #000a,0 0 8em #4f46e544;padding:2.2em 1.5em;font-size:1.05em;animation:pop .7s both,fl 5s ease-in-out infinite;display:flex;flex-direction:column;gap:.9em;position:relative;overflow:hidden}
.ph h4{margin:0;font-size:1.25em}.ph small,.mut{color:var(--mut);font-size:.85em}
.card{background:#ffffff10;border:1px solid #ffffff1c;backdrop-filter:blur(8px);border-radius:1.1em;padding:.9em 1em;display:flex;justify-content:space-between;align-items:center;opacity:0;animation:up .6s forwards}
.card:nth-child(2){animation-delay:.3s}.card:nth-child(3){animation-delay:.6s}.card:nth-child(4){animation-delay:.9s}.card:nth-child(5){animation-delay:1.2s}
.hl{border-color:var(--ind2);box-shadow:0 0 2em #818cf855}
.btn{background:var(--ind);border-radius:1em;padding:.9em;text-align:center;font-weight:700;animation:pop .5s 1.6s both,pulse 1.6s 2.2s infinite}
.btn.g{background:#059669}
@keyframes pulse{50%{box-shadow:0 0 0 .7em #4f46e533}}
.big{font-size:2.4em;font-weight:800;text-align:center}
.st{color:#fbbf24;letter-spacing:.15em;text-align:center}
.qr{display:grid;grid-template-columns:repeat(11,1fr);gap:2px;width:11em;height:11em;margin:0 auto;position:relative;background:#fff;padding:.6em;border-radius:.8em}
.qr i{background:#fff}.qr i.k{background:#0d1224}
.scan{position:absolute;left:0;right:0;height:.25em;background:var(--ok);box-shadow:0 0 1em var(--ok);animation:sc 1.6s ease-in-out infinite}
@keyframes sc{0%{top:5%}50%{top:92%}100%{top:5%}}
.ok{color:var(--ok);font-weight:700;text-align:center;opacity:0;animation:pop .5s 2s forwards}
.ring{width:13em;height:13em;margin:0 auto;position:relative}
.ring svg{transform:rotate(-90deg)}.ring circle{fill:none;stroke-width:7;stroke-linecap:round}
.ring .v{stroke:var(--ind2);stroke-dasharray:251;animation:rg 6s linear forwards}
@keyframes rg{from{stroke-dashoffset:40}to{stroke-dashoffset:150}}
.ring b{position:absolute;inset:0;display:flex;align-items:center;justify-content:center;font-size:1.7em;font-variant-numeric:tabular-nums}
.pair{display:grid;grid-template-columns:1fr 1fr;gap:.6em}
.pic{height:6em;border-radius:1em;background:linear-gradient(135deg,#312e81,#6366f1);display:flex;align-items:center;justify-content:center;font-size:2em}
.bd{display:flex;gap:.4em;flex-wrap:wrap;justify-content:center}.bd span{background:#34d39922;color:var(--ok);border-radius:2em;padding:.3em .7em;font-size:.8em;opacity:0;animation:pop .4s forwards}
.bd span:nth-child(2){animation-delay:2.4s}.bd span:nth-child(3){animation-delay:2.8s}.bd span:nth-child(1){animation-delay:2s}
.lg{display:flex;align-items:center;gap:.5em;font-size:4.6em;font-weight:800;letter-spacing:-.03em;animation:pop .9s both;justify-content:center}
.lg em{color:var(--ind2);font-style:normal}
.orb{position:relative;width:60em;height:22em}
.orb div{position:absolute;padding:.7em 1.2em;border-radius:1em;background:#ffffff12;border:1px solid #ffffff22;backdrop-filter:blur(8px);font-size:1.1em;animation:pop .6s both,fl 5s ease-in-out infinite}
.badge{display:inline-block;border:1px solid #ffffff44;border-radius:.9em;padding:.7em 1.6em;font-weight:700;font-size:1.4em;animation:pop .6s 2.6s both}
#ct{display:flex;gap:10px;align-items:center;width:min(100vw,160vh);padding:0 6px;box-sizing:border-box}
#ct button{background:#1e2547;color:#fff;border:0;border-radius:8px;padding:8px 14px;font:inherit;cursor:pointer}
#pb{flex:1;height:8px;background:#1e2547;border-radius:8px;cursor:pointer;overflow:hidden}#pf{height:100%;width:0;background:var(--ind2)}
#tm{color:var(--mut);font-size:13px;min-width:70px;text-align:right}
</style></head><body>
<div id="st"></div>
<div id="ct"><button id="pp">Pause</button><button id="rs">Restart</button><div id="pb"><div id="pf"></div></div><span id="tm">0s / 72s</span></div>
<script>
const L=(s=1)=>`<svg width="${4*s}em" height="${4*s}em" viewBox="40 12 130 168" fill="none" stroke-width="16" stroke-linejoin="round" stroke-linecap="round"><path d="M62 96H114A31 31 0 0 1 114 158H62Z" stroke="#6366f1"/><path d="M62 30H98A21 21 0 0 1 98 72H62Z" stroke="#c7d2fe"/></svg>`;
const qr=()=>{let s=7,h='';for(let i=0;i<121;i++){s=(s*73+11)%97;h+=`<i class="${s%2?'k':''}"></i>`}return h};
const C=(a,b,c='')=>`<div class="card ${c}"><span>${a}</span><span class="mut">${b}</span></div>`;
const S=[
{t:6,x:["Need something for just a few hours?","Why buy it?"],p:`<small>🔋 Battery 5%</small><div class="card hl"><span>⚡ Low battery</span></div><div class="mut">Laptop Charger</div><div class="big">৳1,200</div>`},
{t:12,c:1,x:["Meet BorrowBox","A university-focused product sharing platform."],p:0,h:`<div class="lg">${L(1)}Borrow<em>Box</em></div>`},
{t:19,x:["Find what you need, when you need it."],p:`<h4>Discover</h4>${C('🔌 Laptop Charger','৳20/h','hl')}${C('🧮 Calculator','৳10/h')}${C('🔋 Power Bank','৳15/h')}${C('📷 Camera','৳80/h')}<small>University → Dept → Availability → Price</small>`},
{t:25,x:["Choose your time.","Send a request."],p:`<h4>Laptop Charger</h4><div class="st">★★★★★</div><div class="card"><span>Trust Score</span><b>92</b></div><div class="card"><span>৳20 / hour</span><span class="mut">2:00 to 6:00 PM</span></div><div class="card"><span>Duration</span><b>2 Hours</b></div><div class="btn">Request to Borrow</div>`},
{t:30,x:["Owner accepts.","Rental confirmed."],p:`<small>New Rental Request</small><div class="card hl"><span>Laptop Charger · 2 Hours</span><b>৳40</b></div><div class="btn g">Accept Request</div><div class="ok" style="animation-delay:2.4s">✓ Rental Confirmed</div>`},
{t:37,x:["Simple. Verified.","Secure."],p:`<h4>Start Handover</h4><div class="qr">${qr()}<div class="scan"></div></div><div class="ok">✓ Handover Verified</div><div class="ok" style="animation-delay:2.8s">Rental Started</div>`},
{t:43,x:["Your rental. Your time.","Never lose track."],p:`<div class="ring"><svg viewBox="0 0 100 100" width="100%"><circle cx="50" cy="50" r="40" stroke="#ffffff18"/><circle class="v" cx="50" cy="50" r="40"/></svg><b id="tt">01:32:45</b></div><div class="mut" style="text-align:center">Laptop Charger · ৳20/hour</div><div class="card hl" style="animation-delay:3s"><span>⏰ Rental ending soon</span><span class="mut">15 min</span></div>`},
{t:50,x:["Return with confidence.","Check. Verify. Complete."],p:`<h4>Return Product</h4><div class="pair"><div><small>Before</small><div class="pic">🔌</div></div><div><small>After</small><div class="pic">🔌</div></div></div><div class="ok" style="animation-delay:1.6s">✓ Condition Verified</div>`},
{t:58,x:["Build trust with every interaction."],p:`<small>2 Hours × ৳20</small><div class="big">৳40</div><div class="ok" style="animation-delay:.5s">✓ Payment Completed</div><div class="st">★★★★★</div><div class="mut" style="text-align:center">Trust Score</div><div class="big" style="color:var(--ind2)"><b class="n" data-f="92" data-t="94">92</b></div><div class="bd"><span>✓ Reliable</span><span>✓ Verified</span><span>✓ On-Time</span></div>`},
{t:63,x:["Need it now?","Send an Emergency Request."],p:`<div class="btn" style="background:#dc2626;animation-delay:0s">Emergency Request</div>${C('🧮 Scientific Calculator','Available Now','hl')}<div class="btn" style="animation-delay:1.2s">Request Now</div>`},
{t:72,c:1,x:["Share what you have.","Borrow what you need."],p:0,h:`<div class="lg">${L(1)}Borrow<em>Box</em></div>`,f:`<div class="mut" style="font-size:1.6em;animation:up .8s 1.6s both">Built for Students. Powered by Community.</div><div class="badge">Available on Android</div><div class="mut" style="animation:up .8s 3.4s both">Discover • Borrow • Return • Repeat</div>`}
];
const st=document.getElementById('st'),D=72;
st.innerHTML=S.map(s=>`<section class="sc ${s.c?'ctr':''}">${s.c?`<div class="txt">`:''}${s.h||''}${s.c?'':'<div class="txt">'}${s.x.map(a=>`<h2>${a}</h2>`).join('')}</div>${s.f||''}${s.p?`<div class="ph">${s.p}</div>`:''}</section>`).join('');
const sc=[...st.children];let t=0,pl=true,cur=-1,last=performance.now();
const pp=document.getElementById('pp'),pf=document.getElementById('pf'),tm=document.getElementById('tm');
function show(i){sc.forEach((e,j)=>e.classList.toggle('on',j==i));cur=i}
function tick(n){const dt=(n-last)/1000;last=n;if(pl){t+=dt;if(t>=D)t=0}
let i=S.findIndex(s=>t<s.t);if(i<0)i=S.length-1;if(i!=cur)show(i);
const s0=i?S[i-1].t:0,el=sc[i],tt=el.querySelector('#tt');
if(tt){let v=Math.max(0,5565-(t-s0)*260|0);tt.textContent=[v/3600|0,v/60%60|0,v%60].map(x=>String(x).padStart(2,'0')).join(':')}
el.querySelectorAll('.n').forEach(n=>{const f=+n.dataset.f,e=+n.dataset.t,k=Math.min(1,Math.max(0,(t-s0-2.5)/2));n.textContent=Math.round(f+(e-f)*k)});
pf.style.width=t/D*100+'%';tm.textContent=(t|0)+'s / '+D+'s';requestAnimationFrame(tick)}
pp.onclick=()=>{pl=!pl;pp.textContent=pl?'Pause':'Play'};
document.getElementById('rs').onclick=()=>{t=0;cur=-1;pl=true;pp.textContent='Pause'};
document.getElementById('pb').onclick=e=>{const r=e.currentTarget.getBoundingClientRect();t=(e.clientX-r.left)/r.width*D;cur=-1};
requestAnimationFrame(tick);
</script></body></html>
